package com.berryjam.bashim

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.berryjam.bashim.data.SearchRepository
import com.berryjam.bashim.data.SearchRepositoryProvider
import com.berryjam.bashim.data.SourceOfQuotes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val tag: String = "MainActivity"

class MainActivity : AppCompatActivity() {

    @BindView(R.id.list)
    lateinit var listView: RecyclerView

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepository()

    private val list: MutableList<SourceOfQuotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm

        compositeDisposable.add(
                repository.searchSourcesOfQuotes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                { result ->
                                    result.forEach {
                                        list.addAll(it)
                                    }
                                    listView.adapter = SourceOfQuotesAdapter(list)
                                    Log.d(tag, list.toString())
                                },
                                { error -> Log.e(tag, error.message) }
                        )

        )
    }
}
