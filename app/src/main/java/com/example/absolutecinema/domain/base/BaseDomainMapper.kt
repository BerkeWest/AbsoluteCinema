package com.example.absolutecinema.domain.base

interface BaseDomainMapper<Data : BaseDataModel, Domain : BaseDomainModel> {
    fun Data.toDomain(): Domain
    fun Domain.toData(): Data
}

interface BaseDomainModel
interface BaseDataModel