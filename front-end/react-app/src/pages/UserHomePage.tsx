// UserHomePage.tsx
//
// Show published posts
// - contains username (first + last), date, and post title
// - sorted by date (newest first)
// - (bonus) Other sorting and filtering posts
// - (low priority) Pagination (back-end / front-end)
//
// A button to create a new post, which will open a new page or a pop window for users to create a new post


import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { PostOnHomePage, getPosts } from '../utils/getPosts';
import { backendUrl, getPostByIdPath } from '../utils/url';

const UserHomePage: React.FC = () => {
  // States
  const [posts, setPosts] = React.useState<PostOnHomePage[]>([]); // store the posts of the user

  const navigate = useNavigate();

  const createPostUrl = '/create-post'; // front-end route for creating a new post

  // Fetch the posts of the user
  useEffect(() => {
    getPosts().then((data) => {
      // fetch the name of the user
      // TODO: add post type declaration
      
      setPosts(data);
      console.log(data);
    })
    .catch((error) => {
      console.error('Failed to fetch posts', error);
    });
    }, []);

    // TODO: waiting for composite data
  return  (
    <>
    <h1>User Home Page</h1>
    <button onClick={() => navigate(createPostUrl)}>Create a new post</button>
    <div>
      <h2>Published Posts</h2>
      {posts.map(post => (
        <div key={post.postId}>
          <Link to={backendUrl + getPostByIdPath(post.postId)}>{post.title}</Link>
          {/* <p>By {post.firstName} {post.lastName} on {post.dateCreated}</p> */}
          <p>By user{post.userId} on {post.dateCreated}</p>
        </div>
      ))}
    </div>
  </>
  
  );
};

export default UserHomePage;
