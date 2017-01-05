package com.example.kemo.socializer.View.CommentsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.AttachmentSender;
import com.example.kemo.socializer.SocialAppGeneral.Comment;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.Relations;
import com.example.kemo.socializer.View.Adapters.CommentsAdapter;

/**
 * A placeholder fragment containing a simple editText.
 */
public class CommentsActivityFragment extends Fragment {
    private CommentsAdapter commentsAdapter;
    private Post post;
    public CommentsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_comments, container, false);
        view.findViewById(R.id.send_button).setOnClickListener(new CommentClick(view.findViewById(R.id.commentWriter_EditText)));
        post = Post.fromJsonString((getActivity()
                .getIntent()
                .getStringExtra(Intent.EXTRA_TEXT)));
        commentsAdapter = new CommentsAdapter(
                post.getComments()
                ,getActivity());
        ListView listView = (ListView) view.findViewById(R.id.comments_listView);
        listView.setAdapter(commentsAdapter);
        return view;
    }
    private class CommentClick implements View.OnClickListener
    {
        private View editText;

        CommentClick(View editText) {
            this.editText = editText;
        }
        @Override
        public void onClick(View view) {
            if (!((EditText)editText).getText().toString().equals(""))
            {
                final Comment  comment = createComment(((EditText)editText).getText().toString());
                new ClientLoggedUser.addComment(new AttachmentSender(comment, post.getOwnerId(), post.getId())) {
                    @Override
                    public void onFinish(Post post) {
                        CommentsActivityFragment.this.post = post;
                        commentsAdapter.getComments().clear();
                        commentsAdapter.setComments(post.getComments());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commentsAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                };
                ((EditText)editText).setText("");
            }
        }
        private Comment createComment(String text)
        {
            Comment comment = new Comment();
            comment.setCommentcontent(text);
            comment.setCommentId(0);
            comment.setOwnerID(Long.parseLong( ClientLoggedUser.id));
            comment.setShow(Relations.ADD);
            return comment;
        }
    }
}
