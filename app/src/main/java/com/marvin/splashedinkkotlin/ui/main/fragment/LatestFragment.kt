package com.marvin.splashedinkkotlin.ui.pager_main.fragment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.bean.PhotoBean
import com.marvin.splashedinkkotlin.ui.main.adapter.MainAdapter
import com.marvin.splashedinkkotlin.ui.particulars.ParticularsActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LatestFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LatestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LatestFragment : Fragment(),
        Observer<MutableList<PhotoBean>>,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private val data: MutableList<PhotoBean> = ArrayList()

    private var adapter: MainAdapter? = null
    private var page = 1

    private val per_page = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_latest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        adapter = MainAdapter(activity, R.layout.main_item, data)
        adapter?.setOnLoadMoreListener(this, recycler)
        adapter?.onItemClickListener = this
        adapter?.openLoadAnimation()

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter

        swipe.setOnRefreshListener(this)

        getPhotos(page, per_page)
    }

    fun getPhotos(page: Int, per_page: Int) {
        swipe.isRefreshing = true
        val observable = MyApplication.retrofitService.getPhotoList(page, per_page, "latest")
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onLatestFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        fun onLatestFragmentInteraction(uri: Uri)

    }

    companion object {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @return A new instance of fragment LatestFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String): LatestFragment {
            val fragment = LatestFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }

    }

    var disposable: Disposable? = null

    override fun onNext(t: MutableList<PhotoBean>) {
        if (t.size != 0) {
            if (page == 1) {
                this.data.clear()
                this.data.addAll(t)
                adapter?.notifyDataSetChanged()
            } else {
                adapter?.addData(t)
            }
            if (t.size < 20) {
                adapter?.loadMoreEnd()
            } else {
                adapter?.loadMoreComplete()
            }
        } else {
            adapter?.loadMoreEnd()
        }
    }

    override fun onComplete() {
        swipe.isRefreshing = false
        disposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onError(e: Throwable) {
        e.message?.let { toast(it) }
        swipe.isRefreshing = false
        disposable?.dispose()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, "image")
        val intent = Intent(activity, ParticularsActivity::class.java)
        intent.putExtra("PHOTO_ID", data.get(position).id)
        intent.putExtra("IMAGE_URL", data.get(position).urls?.regular)
        intent.putExtra("HEIGHT", view?.height)
        startActivity(intent, options.toBundle())
    }

    override fun onLoadMoreRequested() {
        page = ++page
        getPhotos(page, per_page)
    }

    override fun onRefresh() {
        page = 1
        getPhotos(page, per_page)
    }
}// Required empty public constructor
