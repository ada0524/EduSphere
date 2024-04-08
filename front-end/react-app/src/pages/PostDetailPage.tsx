// Displays the post details, description, user name, user profile picture, and posted date.
// - (optional) Also show the last modified date
// - Back to home button
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Post } from '../utils/post';

const PostDetailPage: React.FC = () => {
  const { id: postId } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [post, setPost] = useState<Post | null>(null);
  const [replyInput, setReplyInput] = useState<string>('');
  const [showSubReplyInputs, setShowSubReplyInputs] = useState<boolean[]>([]); // Array to track whether to show subreply input for each reply
  const [subReplyInputs, setSubReplyInputs] = useState<string[]>([]); // Array to store subreply inputs for each reply

  const fetchPost = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:9000/post-and-reply-service/posts/${postId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch post');
      }
      const postData = await response.json();
      setPost(postData);
      // Initialize showSubReplyInputs and subReplyInputs arrays
      setShowSubReplyInputs(Array(postData.postReplies.length).fill(false));
      setSubReplyInputs(Array(postData.postReplies.length).fill(''));
    } catch (error) {
      console.error('Error fetching post:', error);
    }
  };

  useEffect(() => {
    fetchPost();
  }, [postId]);

  const handleReply = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:9000/post-and-reply-service/posts/${postId}/replies`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          comment: replyInput,
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to add reply');
      }
      await fetchPost();
      console.log('Reply added successfully');
      setReplyInput('');
    } catch (error) {
      console.error('Error replying to post:', error);
    }
  };

  const handleAddSubReply = async (index: number) => {
    // Show subreply input for the specified reply index
    const newShowSubReplyInputs = [...showSubReplyInputs];
    newShowSubReplyInputs[index] = true;
    setShowSubReplyInputs(newShowSubReplyInputs);
  };

  const handleSubmitSubReply = async (index: number) => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:9000/post-and-reply-service/posts/${postId}/${index}/subreplies`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          comment: subReplyInputs[index], // Get subreply input for the specified reply index
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to add subreply');
      }
      await fetchPost();
      console.log('Subreply added successfully');
      // Hide subreply input after submission
      const newShowSubReplyInputs = [...showSubReplyInputs];
      newShowSubReplyInputs[index] = false;
      setShowSubReplyInputs(newShowSubReplyInputs);
      setSubReplyInputs(prevState => {
        const newSubReplyInputs = [...prevState];
        newSubReplyInputs[index] = '';
        return newSubReplyInputs;
      });
    } catch (error) {
      console.error('Error adding subreply:', error);
    }
  };

  const handleDeleteReply = async (replyIdx: number) => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:9000/post-and-reply-service/posts/${postId}/replies/${replyIdx}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('Failed to delete reply');
      }
      await fetchPost();
      console.log('Reply deleted successfully');
    } catch (error) {
      console.error('Error deleting reply:', error);
    }
  };

  if (!post) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <button onClick={() => navigate('/home')}>Back to Home</button>
      <h1>{post.title}</h1>
      <p>{post.content}</p>
      <p>Posted by: {post.userId}</p>
      <p>Posted Date: {post.dateCreated}</p>
      {post.dateModified && <p>Last Updated: {post.dateModified}</p>}
      {post.attachments.length > 0 && (
        <div>
          <h2>Attachments:</h2>
          <ul>
            {post.attachments.map((attachment, index) => (
              <li key={index}>{attachment}</li>
            ))}
          </ul>
        </div>
      )}
      <div>
        <h2>Replies:</h2>
        {post.postReplies && (
          <ul>
            {post.postReplies.map((reply, index) => (
              <li key={reply.replyId}>
                <p>{reply.comment}</p>
                <button onClick={() => handleDeleteReply(index)}>Delete</button>
                <button onClick={() => handleAddSubReply(index)}>Add Sub Reply</button>
                {showSubReplyInputs[index] && (
                  <div>
                    <textarea value={subReplyInputs[index]} onChange={(e) => setSubReplyInputs(prevState => {
                      const newSubReplyInputs = [...prevState];
                      newSubReplyInputs[index] = e.target.value;
                      return newSubReplyInputs;
                    })} />
                    <button onClick={() => handleSubmitSubReply(index)}>Submit Sub Reply</button>
                  </div>
                )}
                {reply.subReplies && (
                  <ul>
                    {reply.subReplies.map(subReply => (
                      <li key={subReply.subReplyId}>
                        <p>{subReply.comment}</p>
                      </li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>
      <div>
        <h2>Reply to Post:</h2>
        <textarea value={replyInput} onChange={(e) => setReplyInput(e.target.value)} />
        <button onClick={handleReply}>Reply</button>
      </div>
    </div>
  );
};

export default PostDetailPage;

