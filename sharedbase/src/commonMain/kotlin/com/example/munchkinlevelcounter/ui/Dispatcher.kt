package com.example.munchkinlevelcounter.ui

class Dispatcher {
    protected val stores = mutableSetOf<AbstractStore<*>>()

    fun handle(action: IAction) {
        stores.forEach {
            it.handle(action)
        }
    }

    fun add(store: AbstractStore<*>) {
        stores.add(store)
    }

    fun remove(store: AbstractStore<*>) {
        stores.remove(store)
    }
}
