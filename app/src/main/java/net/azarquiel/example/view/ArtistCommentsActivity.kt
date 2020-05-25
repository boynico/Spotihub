package net.azarquiel.example.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_artist_comments.*
import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterFirebase
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.ArtistComment
import net.azarquiel.example.viewmodel.ArtistCommentsViewModel
import org.jetbrains.anko.*
import java.time.Instant
import java.time.format.DateTimeFormatter

class ArtistCommentsActivity : AppCompatActivity() {
    private lateinit var adapter: AdapterFirebase
    private lateinit var artist: Artist
    private lateinit var viewModel: ArtistCommentsViewModel
    private lateinit var db: FirebaseFirestore
    private var ArtistComment: ArrayList<ArtistComment> = ArrayList()
    val FLAGS =
        (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_comments)
        artist = intent.getSerializableExtra("artist") as Artist
        db = FirebaseFirestore.getInstance()
        setListenerComments()
        initRVComments()
        hideNavigation()
        btn_sendcomment.setOnClickListener {
            onClickSendComment()
        }
    }

    //listener de Comments de FIREBASE
    private fun setListenerComments() {
        val docRef = db.collection("ArtistComments")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                documentToList(snapshot.documents)
                adapter.setArtistComments(ArtistComment)
                /*ArtistComment.forEach {
                    it.user_id = getUser(it.user_id)
                }*/
            } else {
                Log.d("TAG4444444", "Current data: null")
            }
        }
    }

    private fun documentToList(documents: List<DocumentSnapshot>) {
        ArtistComment.clear()
        documents.forEach { d ->
            val user_id = d["user_id"] as String
            val artist_id = d["artist_id"] as String
            val comment = d["comment"] as String
            val timestamp = d["timestamp"] as String
            val user_img = d["user_img"] as String
            if (artist_id == artist.id) {
                ArtistComment.add(
                    ArtistComment(
                        user_id = user_id,
                        artist_id = artist_id,
                        comment = comment,
                        timestamp = timestamp,
                        user_img = user_img
                    )
                )
            }
        }
    }

    private fun comentar(comment: String) {
        viewModel = ViewModelProviders.of(this).get(ArtistCommentsViewModel::class.java)
        viewModel.getUserData().observe(this, Observer { it ->
            it?.let {
                val ArtistComment: MutableMap<String, Any> = HashMap() // diccionario key value
                ArtistComment["user_id"] = it.id
                ArtistComment["artist_id"] = artist.id
                ArtistComment["comment"] = comment
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ArtistComment["timestamp"] = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                }
                if (it.images[0] != null) {
                    ArtistComment["user_img"] = it.images[0].url
                } else {
                    ArtistComment["user_img"] = "https://img.icons8.com/carbon-copy/2x/no-image.png"
                }
                db.collection("ArtistComments")
                    .add(ArtistComment)
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.id)
                    })
                    .addOnFailureListener(OnFailureListener { e ->
                        Log.w("TAG", "Error adding document", e)
                    })

            }
        })
    }

    private fun onClickSendComment() {
        alert {
            title = "Comentario"
            customView {
                verticalLayout {

                    lparams(width = wrapContent, height = wrapContent)
                    val etComment = editText {
                        hint = "Escriba su comentario..."
                        padding = dip(16)
                    }
                    positiveButton("Enviar") {
                        if (etComment.text.toString().isEmpty())
                            toast("Campo obligatorio")
                        else {
                            comentar(etComment.text.toString())
                        }
                    }
                }
                negativeButton("Cancelar") {}
            }
        }.show()
    }

    private fun initRVComments() {
        adapter = AdapterFirebase(this, R.layout.row_comments)
        rv_comments.adapter = adapter
        rv_comments.layoutManager = LinearLayoutManager(this)
    }

    fun hideNavigation() {
        window.decorView.apply {
            systemUiVisibility = FLAGS
        }
    }
}
