package open.movie.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private var isFirst: Boolean = false
    private var rootView: View? = null
    private var isFragmentVisible: Boolean = false
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) rootView = inflater.inflate(getLayoutResources(), container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) isFragmentVisible = true
        if (rootView == null) return
        if (!isFirst && isFragmentVisible) { //可见，并且没有加载过
            onFragmentVisibleChange(true)
            return
        }
        if (isFragmentVisible) {  //由可见——>不可见 已经加载过
            onFragmentVisibleChange(false)
            isFragmentVisible = false
        }
    }

    protected open fun onFragmentVisibleChange(b: Boolean) = Unit
    abstract fun getLayoutResources(): Int
    abstract fun initView()
}