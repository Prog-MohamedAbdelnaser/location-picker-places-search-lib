package com.softtech.android_structure.features.chat.fragmentes

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources
import com.softtech.android_structure.R
import com.softtech.android_structure.base.dialogs.AlertDialogManager
import com.softartch_lib.component.fragment.BaseFragment
import com.softtech.android_structure.domain.entities.ChatMessage
import com.softtech.android_structure.domain.entities.MessageParams
import com.softtech.android_structure.domain.entities.Room
import com.softtech.android_structure.domain.firbase.FireBaseMessageUseCase
import com.softtech.android_structure.domain.firbase.FirebaseConstants
import com.softtech.android_structure.features.FeatureConstants
import com.softtech.android_structure.features.chat.ChatRecycleAdapter
import com.softtech.android_structure.features.chat.activities.ChatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.popup_pick_image.*
import com.google.firebase.storage.FirebaseStorage
import android.database.Cursor
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.softtech.android_structure.features.common.showSnackbar








class ChatFragment: BaseFragment() {


    private var room:Room?=null

    var fireBaseMessageUseCase:FireBaseMessageUseCase?=null

    private val chatRecycleAdapter : ChatRecycleAdapter by lazy { ChatRecycleAdapter() }

    override fun layoutResource(): Int = R.layout.fragment_chat

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        room = getRoomFromArgs()
        fireBaseMessageUseCase = FireBaseMessageUseCase(FirebaseDatabase.getInstance().reference)
        initChatRecycleView()
        initEventHandler()
        initMessageEvent(room)

        listAllFiles()
    }

    @Throws(IllegalArgumentException::class)
    private fun getRoomFromArgs(): Room? {
        return  if (requireActivity().intent.extras !=null &&requireActivity(). intent.extras!!.containsKey(FeatureConstants.KEY_ROOM)){
            requireActivity(). intent.extras?.getParcelable(FeatureConstants.KEY_ROOM)
        }else
            throw IllegalArgumentException("${this.javaClass.simpleName} arguments must contains order params")

    }

    private fun initChatRecycleView() {
        listViewChat.apply {
            adapter = chatRecycleAdapter
            setHasFixedSize(true)
            verticalScrollbarPosition = View.SCROLL_INDICATOR_START
            layoutManager = object : LinearLayoutManager(context) {
                override fun isAutoMeasureEnabled(): Boolean {
                    return false
                }
            }
            scrollToPosition(chatRecycleAdapter.getLastPosition())
            addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
                override fun onChildViewDetachedFromWindow(view: View) {

                }

                override fun onChildViewAttachedToWindow(view: View) {
                    val chatMessage = chatRecycleAdapter.getItems()[chatRecycleAdapter.getPosition()]
                    Log.w("onChildViewAttached",chatMessage.readStatus.toString())
                    Log.w("onChildViewAttached msg",chatMessage.text.toString())

                    if (!chatMessage.readStatus && chatMessage.type==2){
                        room?.roomId?.let { fireBaseMessageUseCase?.readMessage(it,chatMessage) }
                    }

                    if (chatRecycleAdapter.isLastPosition) {
                        hideNewMessageAlert()
                    }
                }
            })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val chatMessage = chatRecycleAdapter.getItems()[chatRecycleAdapter.getPosition()]
                    Log.w("onScrolled",chatMessage.readStatus.toString())
                    Log.w("onScrolled msg",chatMessage.text.toString())
                }
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)


                }
            })
        }
    }

    fun scrollChatListToLast(){
        listViewChat.smoothScrollToPosition(chatRecycleAdapter.getLastPosition())
    }

    private fun initMessageEvent(roomFromArgs: Room?) {

        FirebaseDatabase.getInstance().reference
            .child(FirebaseConstants.MESSAGES_COLLECTION_KEY+"/${room?.roomId}/chat").addChildEventListener(object :
                ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.i("onCancelled",p0.message.toString())

                    p0.toException().printStackTrace()
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    val message = p0.getValue(ChatMessage::class.java)
                    message?.apply { this.type = if (senderId.equals(ChatActivity.uid)) 1 else 2 }
                    Log.i("onChildChanged", message?.senderId.toString())
                    val  index = chatRecycleAdapter.getItems().indexOfFirst { chatMessage -> chatMessage.messageId==message?.messageId }
                    message?.let { chatRecycleAdapter.updateItem(it,index) }
                    Log.i("onChildChanged ind", index.toString())
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Log.i("onChildAdded",p0.toString())
                    p0?.let {data->
                        val message = data.getValue(ChatMessage::class.java)
                        message?.apply { this.type = if (senderId.equals(ChatActivity.uid)) 1 else 2 }

                        message?.let { chatRecycleAdapter.addItem(it) }

                        if (!chatRecycleAdapter.isLastPosition && message?.type==2)
                            showNewMessageAlert()
                        else scrollChatListToLast()

                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })
    }

    private fun initEventHandler() {
        btnAttach.setOnClickListener {
            selectImage()
        }
        tvMessageAlert.setOnClickListener {
            if (chatRecycleAdapter.itemCount>0)
                listViewChat.smoothScrollToPosition(chatRecycleAdapter.getLastPosition())
        }
        btnSend.setOnClickListener {

            val timeStampeRef =   FirebaseDatabase.getInstance().reference.child("currentTimeStamp")

            val connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected")

            timeStampeRef.setValue(ServerValue.TIMESTAMP)
            timeStampeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.i("timestampe",p0.value.toString())
                    val timeStampMS:Long=p0.value as Long
                    connectedRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val connected = snapshot.getValue(Boolean::class.java) ?: false
                            if (inputMsg.text.toString().isNotEmpty()){
                                fireBaseMessageUseCase?.sendMessage(MessageParams(ChatActivity.uid,inputMsg.text.toString(),created_at = timeStampMS.toString() ,sendStatus = connected,messageId =timeStampMS ),room?.roomId!!)
                                inputMsg.text.clear()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w("connectedRef", "Listener was cancelled")
                        }
                    })

                }
            })

        }
    }

    fun showNewMessageAlert(){
        tvMessageAlert.visibility=View.VISIBLE
    }

    fun hideNewMessageAlert(){
        tvMessageAlert.visibility=View.GONE
    }
    private fun pickImageFromSource(source: Sources) {


        val rxPermissions = RxPermissions(this)
        val disposable=   rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            .doOnError {}
            .subscribe ({ granted ->
                if (!granted)  {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)&&shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE))
                        pickImageFromSource(source)
                    else{
                        AlertDialogManager.popupGoAppSetting(requireContext())
                    }

                }else{
                    RxImagePicker.with(fragmentManager!!).requestImage(source, getString(R.string.label_pick_image))
                        .subscribe({
                            onImagePicked(it)
                        },{
                            it.printStackTrace()
                        })
                }
            },{ it.printStackTrace() })
    }




    private fun selectImage() {
        val dialog= BottomSheetDialog(context!!)
        dialog.setContentView(R.layout.popup_pick_image)
        dialog.tvCamera.setOnClickListener {
            pickImageFromSource(Sources.CAMERA)
            dialog.dismiss()
        }
        dialog.tvGallery.setOnClickListener {
            pickImageFromSource(Sources.GALLERY)
            dialog.dismiss()
        }
        dialog.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun onImagePicked(uri: Uri) {
        /*  if (result is Bitmap) {
              val newScale= ImageUtility.scaleDown(result,400f,filter = true)
          }

               Timber.i("file path ${getRealPathFromURI(requireContext(),uri)}")
        //Create a file object using file path
        val file = File(getRealPathFromURI(requireContext(),uri))
        // Create a request body with file and image media type
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        // Create MultipartBody.Part using file request-body,file name and part name
        val part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody)

         */
          */


        val storage = FirebaseStorage.getInstance()


        // Create a storage reference from our app
        val storageRef = storage.reference

// Create a reference to "mountains.jpg"
        val mountainsRef = storageRef.child("mountains.jpg")

// Create a reference to 'images/mountains.jpg'
        val mountainImagesRef = storageRef.child("images/mountains.jpg")

// While the file names are the same, the references point to different files
        mountainsRef.name == mountainImagesRef.name // true
        mountainsRef.path == mountainImagesRef.path // false

        val riversRef = storageRef.child("images/${uri.lastPathSegment}")
        var  uploadTask = riversRef.putFile(uri)

// Register observers to listen for when the download is done or if it fails
        riversRef.putFile(uri).addOnFailureListener {
            it.printStackTrace()
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            showSnackbar(view!!,"done")
            val result = it.metadata!!.reference!!.downloadUrl;

            Log.w("Upload","Url ${  it.task.result}")
            result.addOnSuccessListener {
                Log.d("Upload", "onSuccess: uri= "+ uri.toString());
            }
        }.addOnCompleteListener {
            Log.w("Upload","addOnCompleteListener ${  it.result}")

        }

        Glide.with(this /* context */)
            .load("")
            .into(imageView)
    }

    fun listAllFiles() {
        // [START storage_list_all]
        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child("images")

        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { prefix ->
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
                    Log.d("listAllFiles", "onSuccess: uri= "+ prefix.downloadUrl);

                }

                listResult.items.forEach { item ->
                    // All the items under listRef.

                }
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
            }
        // [END storage_list_all]
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }
}