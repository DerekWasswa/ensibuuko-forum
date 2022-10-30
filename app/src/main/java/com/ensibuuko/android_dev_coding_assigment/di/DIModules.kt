package com.ensibuuko.android_dev_coding_assigment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ensibuuko.android_dev_coding_assigment.BuildConfig.DEBUG
import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.api.UserApi
import com.ensibuuko.android_dev_coding_assigment.data.AppDatabase
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import com.ensibuuko.android_dev_coding_assigment.data.models.UserDao
import com.ensibuuko.android_dev_coding_assigment.data.repository.*
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.utils.Constants
import com.ensibuuko.android_dev_coding_assigment.viewmodels.CommentsViewModel
import com.ensibuuko.android_dev_coding_assigment.viewmodels.PostsViewModel
import com.ensibuuko.android_dev_coding_assigment.viewmodels.UsersViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "ensibuuko_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUserDao(database: AppDatabase): UserDao {
        return  database.userDao()
    }

    fun providePostsDao(database: AppDatabase): PostDao {
        return  database.postDao()
    }

    fun provideCommentsDao(database: AppDatabase): CommentDao {
        return  database.commentDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
    single { providePostsDao(get()) }
    single { provideCommentsDao(get()) }

}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    single { provideUserApi(get()) }

    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }
    single { providePostApi(get()) }

    fun provideCommentApi(retrofit: Retrofit): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }
    single { provideCommentApi(get()) }
}

val networkModule = module {
    val connectTimeout : Long = 60
    val readTimeout : Long  = 60

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun provideConnectionDetector(context: Context) : ConnectionDetector {
        return ConnectionDetector(context)
    }

    single { provideHttpClient() }
    single {
        val baseUrl = Constants.BASE_URL
        provideRetrofit(get(), baseUrl)
    }
    single { provideConnectionDetector(androidContext()) }
}

val viewModelModule = module {
    viewModel { PostsViewModel(PostRepositoryImpl(get(), get()) as PostsRepository, get()) }
    viewModel { CommentsViewModel(CommentRepositoryImpl(get(), get()) as CommentRepository, get()) }
    viewModel { UsersViewModel(UserRepositoryImpl(get(), get()) as UserRepository, get()) }
}