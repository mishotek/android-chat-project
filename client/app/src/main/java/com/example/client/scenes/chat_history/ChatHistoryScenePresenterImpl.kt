package com.example.client.scenes.chat_history

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.client.entities.ActiveUsers
import com.example.client.entities.ActiveUsersRequest
import com.example.client.entities.BlockUserRequest
import com.example.client.entities.BlockUserResponse
import com.example.client.network.ApiInterface
import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter
import com.example.client.scenes.chat_history.models.ChatItemModel
import com.example.client.scenes.chat_history.swipe_callback.SwipeToDeleteCallbackDelegate
import com.example.client.scenes.chat_history.view_holders.ChatHistoryListItemViewHolderDelegate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatHistoryScenePresenterImpl(val view: ChatHistorySceneContract.View): ChatHistorySceneContract.Presenter, ChatHistoryListItemViewHolderDelegate,
    SwipeToDeleteCallbackDelegate {


    private val gateway by lazy {
        ApiInterface.initGateway()
    }

    private var currentSkip = 0
    private val limit = 10
    private var totalItemsFetched = 0

    private var queryToSearch = ""

    override var isLoading: Boolean = false

    override fun fetchMoreChatHistory() {
        currentSkip += limit
        fetchChatHistory(currentUserId, currentSkip, limit) { items ->
            totalItemsFetched += items.count()
            val sizeBefore = adapter?.chatItems?.size
            adapter?.chatItems?.addAll(items)
            val sizeAfter = adapter?.chatItems?.size
            adapter?.notifyItemRangeChanged(sizeBefore!!, sizeAfter!!)
            isLoading = false
            Log.d("dbg", "Lazy loaded $limit more items - currentSkip: $currentSkip")
        }
    }

    override fun onSearch(query: String) {
        Log.d("dbg", "Query: $query")
        queryToSearch = if (query.length >= 3) query else ""
    }

    lateinit var mainHandler: Handler

    private var currentUserId: Long = -1
    private var adapter: ChatHistoryListAdapter? = null

    override fun getChatHistoryDataSource(userId: Long, completion: (List<ChatItemModel>) -> Unit) {
        currentUserId = userId
        fetchChatHistory(userId, currentSkip, limit) { items ->
            totalItemsFetched += items.count()
            view.setNoHistoryLabelVisible(items.isEmpty())
            completion(items)
        }
    }

    override fun setAdapter(adapter: ChatHistoryListAdapter) {
        this.adapter = adapter
    }

    override fun onCreate() {
        mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onPause() {
        mainHandler.removeCallbacks(fetchActiveUsersTask)
    }

    override fun onResume() {
        mainHandler.post(fetchActiveUsersTask)
    }

    private fun fetchChatHistory(userId: Long, skip: Int, limit: Int, completion: (List<ChatItemModel>) -> Unit) {
        val call = gateway.getAllActiveUsers(ActiveUsersRequest(userId, skip, limit, queryToSearch))
        call.enqueue(object: Callback<ActiveUsers> {

            override fun onFailure(call: Call<ActiveUsers>, t: Throwable) {
                view.showToastMessage("Active users couldn't be loaded")
            }

            override fun onResponse(call: Call<ActiveUsers>, response: Response<ActiveUsers>) {
                val activeUsers = response.body()
                val shouldProceed = response.code() == 200 && activeUsers != null && activeUsers.success
                if (shouldProceed) {
                    val items = activeUsers!!.users.map { it ->
                        ChatItemModel(
                            it.user.id.toLong(),
                            it.user.nickname,
                            it.lastMessage?.message ?: "",
                            it.user.occupation,
                            it.user.imgBase64,
                            this@ChatHistoryScenePresenterImpl
                        )
                    }

                    completion(items)
                } else {
                    view.showToastMessage("Active users couldn't be loaded")
                }
            }
        })
    }

    override fun didTapChatItem(model: ChatItemModel?) {
        view.startChatActivity(currentUserId, model)
    }

    private val fetchActiveUsersTask = object : Runnable {
        override fun run() {
            if (totalItemsFetched == 0) {
                mainHandler.postDelayed(this, 5000)
                return
            }
            Log.d("dbg", "Active users fetched - range: 0-$totalItemsFetched")
            fetchChatHistory(currentUserId, 0, totalItemsFetched) { items ->
                adapter?.chatItems = items.toMutableList()
                adapter?.notifyDataSetChanged()
            }
            mainHandler.postDelayed(this, 3000)
        }
    }

    override fun didSwipeCellAt(position: Int) {
        val model = adapter?.chatItems?.get(position) ?: return
        val call = gateway.blockUser(BlockUserRequest(currentUserId, model.userId))
        call.enqueue(object: Callback<BlockUserResponse> {

            override fun onFailure(call: Call<BlockUserResponse>, t: Throwable) {
                view.showToastMessage("Network failure: User couldn't be blocked")
            }

            override fun onResponse(call: Call<BlockUserResponse>, response: Response<BlockUserResponse>) {
                val blockUserResponse = response.body()
                val shouldProceed = response.code() == 200 && blockUserResponse != null && blockUserResponse.success
                if (shouldProceed) {
                    adapter?.chatItems?.removeAt(position)
                    adapter?.notifyItemRemoved(position)
                } else {
                    view.showToastMessage("User couldn't be blocked")
                }
            }
        })
    }

}