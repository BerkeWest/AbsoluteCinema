package com.example.absolutecinema.data.model.serializer

import com.example.absolutecinema.data.model.response.RateValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject

object RatedSerializer : KSerializer<RateValue> {
    override val descriptor: SerialDescriptor = RateValue.serializer().descriptor

    override fun serialize(
        encoder: Encoder,
        value: RateValue
    ) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("This class can be saved only by Json")
        if (value.value != null) {
            jsonEncoder.encodeSerializableValue(RateValue.serializer(), value)
        } else {
            jsonEncoder.encodeBoolean(false)
        }
    }

    override fun deserialize(decoder: Decoder): RateValue {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val element = jsonDecoder.decodeJsonElement()

        return if (element is JsonObject) {
            jsonDecoder.json.decodeFromJsonElement(RateValue.serializer(), element)
        } else {
            RateValue(null)
        }
    }
}