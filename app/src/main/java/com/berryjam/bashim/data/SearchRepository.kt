package com.berryjam.bashim.data

import io.reactivex.Observable

class SearchRepository(val apiService: BashImApiService) {

    fun searchQuotes(site: String, name: String): Observable<List<Quote>> {
        return apiService.searchQuotes(site, name, 50)
    }

    fun searchSourcesOfQuotes(): Observable<List<List<SourceOfQuotes>>> {
        return apiService.searchSources()
    }

}
