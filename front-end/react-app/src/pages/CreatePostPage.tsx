import React, { useState } from 'react';
import { backendUrl, uploadPostAttachments, uploadPostImages, createPostPublishedPath, createPostDraftPath} from "../utils/url";
import { useNavigate } from 'react-router-dom';

const CreatePostPage: React.FC = () => {
    const [postTitle, setPostTitle] = useState('');
    const [postText, setPostText] = useState('');
    const [postImage, setImage] = useState<File[]>([]);
    const [postAttachments, setAttachments] = useState<File[]>([]);
    const [imageKeys, setImageKeys] = useState<String[]>([]);
    const [message, setMessage] = useState('');
    const token:string | null = localStorage.getItem('token');

    const handleChangeText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setPostText(e.target.value);
       
    };
    const handleChangeTitle = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setPostTitle(e.target.value);  
    };

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        const selectedFiles = e.target.files;
        if (selectedFiles) {
            const newImages: File[] = Array.from(selectedFiles);
            setImage((prevImages) => [...prevImages, ...newImages]);
            e.target.value = ''; 
        }
    };

    const handleAttachmentUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        const selected = e.target.files;
        if (selected) {
            const newAttach: File[] = Array.from(selected);
            setAttachments((prevAttach) => [...prevAttach, ...newAttach]);
            e.target.value = ''; 
        } 
    };

    const handleRemoveImage = (index: number) => {
        setImage((prevImage) => {
            const newImage = [...prevImage];
            newImage.splice(index, 1);
            return newImage;
        });
    };

    const handleRemoveAttach = (index: number) => {
        setAttachments((prevAttach) => {
            const newAttach = [...prevAttach];
            newAttach.splice(index, 1);
            return newAttach;
        });
    };

    const handlePublish =  async (mode:number) => {
        if(postImage){
            const imageEndpoint:string = backendUrl + uploadPostImages;
            const formData = new FormData();
            postImage.forEach((image, index) => {
                formData.append(`images`, image);
            });
            try {
                const response = await fetch(imageEndpoint, {
                    method: "POST",
                    headers: {
                        "Authorization": "Bearer " + token,
                    },
                    body: formData
                });
    
                if (response.ok) {
                    const data = await response.json();
                    setImageKeys(data.data);
                    console.log(data.data);
                } else {
                    console.error("Error response:", response.status, response.statusText);
                    return;
                }
            } catch (error) {
                console.error("Error uploading images:", error);
                return;
            }
        }

        if(postAttachments){
            const attachEndpoint:string = backendUrl + uploadPostAttachments;
            const formAttach = new FormData();
            postImage.forEach((attach, index) => {
                formAttach.append(`files`, attach);
            });
            try {
                const response = await fetch(attachEndpoint, {
                    method: "POST",
                    headers: {
                        "Authorization": "Bearer " + token,
                    },
                    body: formAttach
                });
    
                if (response.ok) {
                    const data = await response.json();
                    setImageKeys(data.data);
                    console.log(data.data);
                } else {
                    console.error("Error response:", response.status, response.statusText);
                    return;
                }
            } catch (error) {
                console.error("Error uploading attachments:", error);
                return;
            }
        }
        
        if(mode == 0)
            creatPost();
        else
            creatDraft();
        
    };

    const navigate = useNavigate();
    const creatPost = async () => {
        const publishPost:string = backendUrl + createPostPublishedPath;
        const requestBody = {
            "title":postTitle,
            "content":postText,
            "images":imageKeys,
            "attachments":postAttachments
        };
        try {
            const response = await fetch(publishPost, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", 
                    "Authorization": "Bearer " + token,
                },
                body: JSON.stringify(requestBody)
            });

            if (response.ok) {
                setMessage("Your post is published");
                console.log("success");
            } 
            else if(response.status == 401){
                navigate('/login');
            }
            else {
                console.error("Error response:", response.status, response.statusText);
                return;
            }
        } catch (error) {
            console.error("Error creating post:", error);
            return;
        }

    }

    const creatDraft =async () => {
        const draftPost:string = backendUrl + createPostDraftPath;
        const requestBody = {
            "title":postTitle,
            "content":postText,
            "images":imageKeys,
            "attachments":postAttachments,
        };
        try {
            const response = await fetch(draftPost, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", 
                    "Authorization": "Bearer " + token,
                },
                body: JSON.stringify(requestBody)
            });

            if (response.ok) {
                setMessage("Your post is saved");
                console.log("success");
            } 
            else if(response.status == 401){
                navigate('/login');
            }
            else {
                console.error("Error response:", response.status, response.statusText);
                return;
            }
        } catch (error) {
            console.error("Error creating draft:", error);
            return;
        }

    }

    return (
        <div>
            <h2>Create Post</h2>
            <div>            
                <textarea
                value={postTitle}
                onChange={handleChangeTitle}
                placeholder="Title"
                rows={1}
            />
            </div>

            <div>
            <textarea
                value={postText}
                onChange={handleChangeText}
                placeholder="New post"
                rows={4}
            />
            </div>

            <div>
            <label htmlFor="imageUpload">Upload Image:</label>
            <input
                id="uploadImages"
                type="file"
                accept="image/*"
                onChange={handleImageUpload}
                multiple
            />
            <div>
                <ul>
                    {postImage.map((image, index) => (
                        <li key={index}>{image.name}
                        <button onClick={() => handleRemoveImage(index)}>x</button>
                        </li>
                    ))}
                </ul>
            </div>
            </div>

            <div>
            <label htmlFor="attachmentUpload">Upload Attachment:</label>
            <input
                id="uploadAttachments"
                type="file"
                onChange={handleAttachmentUpload}
                multiple
            />
            <div>
                <ul>
                    {postAttachments.map((file, index) => (
                        <li key={index}>{file.name}
                        <button onClick={() => handleRemoveAttach(index)}>x</button>
                        </li>
                    ))}
                </ul>
            </div>
            </div>
            <button onClick={() => handlePublish(0)}>Publish</button>
            <button onClick={() => handlePublish(1)}>Save</button>
            {<p>{message}</p>}
        </div>
    );
};

export default CreatePostPage;
