package com.ensibuuko.android_dev_coding_assigment

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ensibuuko.android_dev_coding_assigment.api.CommentApi
import com.ensibuuko.android_dev_coding_assigment.api.PostApi
import com.ensibuuko.android_dev_coding_assigment.api.UserApi
import com.ensibuuko.android_dev_coding_assigment.data.AppDatabase
import com.ensibuuko.android_dev_coding_assigment.data.models.CommentDao
import com.ensibuuko.android_dev_coding_assigment.data.models.PostDao
import com.ensibuuko.android_dev_coding_assigment.data.models.UserDao
import com.ensibuuko.android_dev_coding_assigment.data.repository.*
import com.ensibuuko.android_dev_coding_assigment.utils.ConnectionDetector
import com.ensibuuko.android_dev_coding_assigment.viewmodels.CommentsViewModel
import com.ensibuuko.android_dev_coding_assigment.viewmodels.PostsViewModel
import com.ensibuuko.android_dev_coding_assigment.viewmodels.UsersViewModel
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class EnsibuukoTestApplication : Application() {

    private val testDatabaseModule = module {

        fun provideDatabase(application: Application): AppDatabase {
            return Room.inMemoryDatabaseBuilder(application, AppDatabase::class.java)
                .allowMainThreadQueries()
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

    private val testApiModule = module {

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

    private val testNetworkModule = module {
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build()
        }

        fun provideRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun provideConnectionDetector(context: Context) : ConnectionDetector {
            return ConnectionDetector(context)
        }

        single { provideHttpClient() }
        single { provideRetrofit(get()) }
        single { provideConnectionDetector(androidContext()) }
    }

    private val testViewModelModule = module {
        viewModel { PostsViewModel(FakePostRepository(), get(), get()) }
        viewModel { CommentsViewModel(FakeCommentRepository(), get(), get()) }
        viewModel { UsersViewModel(FakeUserRepository(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@EnsibuukoTestApplication)
            modules(listOf(testDatabaseModule, testApiModule, testNetworkModule, testViewModelModule))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        GlobalContext.stopKoin()
    }

}