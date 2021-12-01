package com.dagger.infiniterecyclerview.ui.record.model

import com.dagger.infiniterecyclerview.model.History
import com.dagger.infiniterecyclerview.model.RentList

class FamilyModel {
    companion object {
        const val PARENT = 1
        const val CHILD = 2
        const val PROGRESS = 3
    }

    lateinit var parent : History
    lateinit var child : RentList
    lateinit var progress : String

    var type: Int


    constructor(type: Int, parent: History) {
        this.type = type
        this.parent = parent
    }

    constructor(type: Int, child: RentList) {
        this.type = type
        this.child = child
    }

    constructor(type: Int, progress: String) {
        this.type = type
        this.progress = progress
    }
}