package com.berryjam.bashim

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.berryjam.bashim.data.SearchRepository
import com.berryjam.bashim.data.SearchRepositoryProvider
import com.berryjam.bashim.data.SourceOfQuotes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val tag: String = "MainActivity"

class MainActivity : AppCompatActivity() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepository()

    private val list: MutableList<SourceOfQuotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable.add(
                repository.searchSourcesOfQuotes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                { result ->
                                    result.forEach {
                                        list.addAll(it)
                                    }
                                    Log.d(tag, list.toString())
                                },
                                { error -> Log.e(tag, error.message) }
                        )

        )
    }
}
