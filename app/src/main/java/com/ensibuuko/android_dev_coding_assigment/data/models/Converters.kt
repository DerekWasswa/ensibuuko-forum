package com.ensibuuko.android_dev_coding_assigment.data.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters() {
    @TypeConverter
    fun toAddressJson(address: Address) : String {
        return Gson().toJson(
            address,
            object : TypeToken<Address>(){}.type
        ) ?: "{}"
    }

    @TypeConverter
    fun fromAddressJson(json: String): Address {
        return Gson().fromJson<Address>(
            json,
            object: TypeToken<Address>(){}.type
        ) ?: Address("", "", "", "")
    }

    @TypeConverter
    fun toCompanyJson(company: Company) : String {
        return Gson().toJson(
            company,
            object : TypeToken<Company>(){}.type
        ) ?: "{}"
    }

    @TypeConverter
    fun fromCompanyJson(json: String): Company{
        return Gson().fromJson<Company>(
            json,
            object: TypeToken<Company>(){}.type
        ) ?: Company("","", "")
    }
}