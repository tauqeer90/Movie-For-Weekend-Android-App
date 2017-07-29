package android.tauqeer.syed.movieforweekend;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.tauqeer.syed.movieforweekend.RecyclerView.RecyclerViewAdapter;
import android.tauqeer.syed.movieforweekend.Utils.AppConstants;
import android.tauqeer.syed.movieforweekend.Utils.JSONParser;
import android.tauqeer.syed.movieforweekend.Utils.NetworkUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.tauqeer.syed.movieforweekend.Utils.AppConstants.JSON_KEY;
import static android.tauqeer.syed.movieforweekend.Utils.AppConstants.POSITION_KEY;
import static android.tauqeer.syed.movieforweekend.Utils.AppConstants.SAVED_INSTANCE_KEY;

public class HomeScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final int MOVIE_API_LOADER = 28;

    private List<String> allPosterPaths = new ArrayList<>();
    private RecyclerView rcView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        rcView = (RecyclerView) findViewById(R.id.recyclerview);

        Bundle movieBundle = new Bundle();
        movieBundle.putString(AppConstants.API_KEY , AppConstants.API);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieLoader = loaderManager.getLoader(MOVIE_API_LOADER);

        this.getSupportLoaderManager();
        if(movieLoader == null) {
            loaderManager.initLoader(MOVIE_API_LOADER , movieBundle, this).forceLoad();
        } else {
            loaderManager.restartLoader(MOVIE_API_LOADER, movieBundle, this).forceLoad();
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Override
            public String loadInBackground() {
                 return NetworkUtils.readStringFromURLConnection
                         (NetworkUtils.getMovieListURLConnection(AppConstants.APPEND_PATH_POPULAR));
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, final String data) {
        if(data != null) {
            allPosterPaths = JSONParser.getAllPostersPath(data);

            adapter = new RecyclerViewAdapter(this , allPosterPaths );
            rcView.setAdapter(adapter);
            rcView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter.setOnItemClickListener(new RecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(HomeScreen.this, MovieDetailScreen.class);
                    intent.putExtra(JSON_KEY , data);
                    intent.putExtra(POSITION_KEY , position);
                    startActivity(intent);
                    Toast.makeText(HomeScreen.this , "helllooo" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

        adapter.notifyDataSetChanged();
    }

}
