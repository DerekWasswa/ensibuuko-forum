package com.ensibuuko.android_dev_coding_assigment.data.models

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ensibuuko.android_dev_coding_assigment.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(private val jsonParser: JsonParser) {
    @TypeConverter
    fun toAddressJson(address: Address) : String {
        return jsonParser.toJson(
            address,
            object : TypeToken<Address>(){}.type
        ) ?: "{}"
    }

    @TypeConverter
    fun fromAddressJson(json: String): Address {
        return jsonParser.fromJson<Address>(
            json,
            object: TypeToken<Address>(){}.type
        ) ?: Address("", "", "", "", Geo("", ""))
    }

    @TypeConverter
    fun toGeoJson(geo: Geo) : String {
        return jsonParser.toJson(
            geo,
            object : TypeToken<Geo>(){}.type
        ) ?: "{}"
    }

    @TypeConverter
    fun fromGeoJson(json: String): Geo{
        return jsonParser.fromJson<Geo>(
            json,
            object: TypeToken<Geo>(){}.type
        ) ?: Geo("", "")
    }

    @TypeConverter
    fun toCompanyJson(company: Company) : String {
        return jsonParser.toJson(
            company,
            object : TypeToken<Company>(){}.type
        ) ?: "{}"
    }

    @TypeConverter
    fun fromCompanyJson(json: String): Company{
        return jsonParser.fromJson<Company>(
            json,
            object: TypeToken<Company>(){}.type
        ) ?: Company("","", "")
    }
}