// get post data from the back-end
// will be displayed on the user's home page

import { backendUrl, getAllPostsPath } from "./url";

export interface PostOnHomePage {
    // draft design, feel free to modify
    postId: string; // the post id, for diff algorithm optimization
    userId: string; // the user id, for diff algorithm optimization
    firstName: string;
    lastName: string;
    dateCreated: string; // (optional) reformatted date
    title: string;
    // might be more fields
    // 
  }

// first half: return the array of all posts that will be displayed on the home page
export const getPosts = () => {

    const getPostsUrl:string = backendUrl + getAllPostsPath;

    
    const token:string | null = localStorage.getItem('token');
    
    // attach the token to the request header
    return fetch(getPostsUrl, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token,
            
        },
    }).then((response) => {
        if(response.ok) {
            return response.json();
        }
        throw new Error('Failed to fetch posts');
    });

};