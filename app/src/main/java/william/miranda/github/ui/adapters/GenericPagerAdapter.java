package william.miranda.github.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import william.miranda.github.ui.fragments.GenericPagerFragment;
import william.miranda.github.ui.fragments.RepositoryFragment;
import william.miranda.github.ui.fragments.UserFragment;

public class GenericPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * Indica o tipo de fragmento que este Adapter fornece
     */
    private final GenericPagerFragment.PageType pageType;

    /**
     * O adapter sabe a String buscada, para passar para cada Fragment
     */
    private final String query;

    /**
     * Número máximo de páginas
     */
    private int numPages = 1;

    /**
     * Construtor
     * @param query
     * @param fm
     */
    public GenericPagerAdapter(GenericPagerFragment.PageType pageType, String query, FragmentManager fm) {
        super(fm);
        this.pageType = pageType;
        this.query = query;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putString("query", query);
        arguments.putInt("pageNumber", position + 1);//começa na página 1 (e não na 0)

        Fragment fragment;

        if (pageType == GenericPagerFragment.PageType.REPOSITORIES) {
            fragment = new RepositoryFragment();
        } else {
            fragment = new UserFragment();
        }

        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public boolean updateNumPages(int numPages) {
        if (this.numPages != numPages) {
            this.numPages = numPages;
            return true;
        }

        return false;
    }
}
