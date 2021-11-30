package com.dagger.infiniterecyclerview.ui.record.model

import com.dagger.infiniterecyclerview.model.History
import com.dagger.infiniterecyclerview.model.RentList

class FamilyModel {
    companion object {
        const val PARENT = 1
        const val CHILD = 2
    }

    lateinit var parent : History
    lateinit var child : RentList

    var type: Int


    constructor(type: Int, parent: History) {
        this.type = type
        this.parent = parent
    }

    constructor(type: Int, child: RentList) {
        this.type = type
        this.child = child
    }
}