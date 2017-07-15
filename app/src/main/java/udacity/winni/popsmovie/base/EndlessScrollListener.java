package udacity.winni.popsmovie.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessScrollListener.class.getSimpleName();

    public static final int ITEM_VISIBLE_TRESHOLD = 4;

    private int previousTotal = 0; // The total number of items in the dataset after the last load

    private boolean loading = true; // True if we are still waiting for the last set of data to
    // load.

    private final boolean loadMoreAllowed = true;

    private final int visibleThreshold; // The minimum amount of items to have below your current
    // scroll position before loading more.

    private int firstVisibleItem, visibleItemCount, totalItemCount;

    private final LinearLayoutManager gridLayoutManager;

    public EndlessScrollListener(LinearLayoutManager gridLayoutManager, int visibleThreshold) {
        this.gridLayoutManager = gridLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = gridLayoutManager.getItemCount();
        firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

        if (totalItemCount < previousTotal) {
            previousTotal = 0;
            if (totalItemCount == 0) {
                loading = true;
            }
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (loadMoreAllowed && !loading && (totalItemCount - visibleItemCount) <=
            (firstVisibleItem + visibleThreshold)) {
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();
}