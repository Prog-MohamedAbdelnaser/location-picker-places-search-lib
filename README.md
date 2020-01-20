# location-picker-places-search-lib
An Android library that provides a location picker and auto complete search about locations

# Usage
For a working implementation of this project see the sample/ folder.

### Step 1

Include the library as a local library project or add the dependency in your build.gradle.

```groovy
dependencies {
	        implementation 'com.github.Prog-MohamedAbdelnaser:softartch-lib:Tag'
}
```
Or

Import the library, then add it to your /settings.gradle and /app/build.gradle. 

### Step 2
Make you owen map fragment child of LocationPickerFragmentWithSearchBar.



    class AddressFragmentWithSearchBar : LocationPickerFragmentWithSearchBar(){

    //initialize map view with parent map view

    override fun mapViewResource(): MapView =mapView

    //inflate your owen fragment design in  parent fragment design 
    override fun layoutResource(): Int = R.layout.fragment_location

    // handle click action when user select location form auto complete search result
    override fun clickPickedPlace(locationName:String) {
        searchViewAuto.getRecycleViewResults()!!.hide()
        progressBar.hide()
        tvSearchPlaceHolderMessage.hide()
        //searchViewAuto.getSearchView()?.setQuery("", true);
        searchViewAuto.getSearchView()?.clearFocus()

    }
    
    // handle action when user start write search query in auto complete search view
    override fun onAutoCompleteSearchStart() {
        searchViewAuto.getRecycleViewResults()!!.show()
        progressBar.show()

    }

    // handle action when get search result 
    override fun onAutoCompleteSearchFinised(resultIsNotEmpty: Boolean) {
        searchViewAuto.getRecycleViewResults()!!.show()
        progressBar.hide()
        if (resultIsNotEmpty.not()){
            searchViewAuto.getRecycleViewResults()!!.hide()
            tvSearchPlaceHolderMessage.show()
        }

    }

    // handler for on fragment view inflated "onCreatView" get search result 
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        /*
	to fillter auto complete  search result 
	* if you want to make search result in specific country Like EG for egypte
	*/
	
        // setSearchLocalizationFilter(SAUDIA_FILTER)

        initRecycleView()

        initAutoSearchQuery()

	     
        // to initialize map location pin 
        setMapPickLoctionIcon(R.drawable.ic_location)
    
    }

    private fun initAutoSearchQuery() {

        searchViewAuto.getSearchView()?.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty().not()) {
                    searchQueryListener(newText.toString())
                }else{
                    searchViewAuto.getRecycleViewResults()!!.hide()
                }
                return false
            }
        })
    }

    private fun initRecycleView() {

        // todo  initialize recycler in search view adapter to set in it auto complete search result adapter
        
        val adapter=getAutoCompleteSearchResultAdapter()
        searchViewAuto.setAdapter(adapter)
    }

    override fun onGetLocationAddress(locationAddress: LocationAddress) {

        // todo handle as you need the pick location result or location selected from search

        Log.i("onGetLocationAddress","${locationAddress.toString()}")
    }
}
### Step 3

Add com.softartch_lib.component.widget.AutoCompleteSearchView  and map view to map fragment xm file .


    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical"
                >
                <com.softartch_lib.component.widget.AutoCompleteSearchView
                    android:id="@+id/searchViewAuto"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    />

                <ProgressBar
                    android:id="@+id/progressBar"
                    android:layout_width="match_parent"
                    android:layout_margin="8dp"
                    android:visibility="gone"
                        android:layout_height="20dp"/>

                <androidx.appcompat.widget.AppCompatTextView
                    android:id="@+id/tvSearchPlaceHolderMessage"
                    android:text="@string/no_result"
                    android:layout_width="match_parent"
                    android:visibility="gone"
                    android:layout_margin="8dp"
                    android:layout_height="20dp"/>

            </LinearLayout>

    </androidx.appcompat.widget.Toolbar>

    <com.google.android.gms.maps.MapView
        android:id="@+id/mapView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:cameraTargetLat="23.91895493226023"
        app:cameraTargetLng="45.43327666819095"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/toolbar2" />

