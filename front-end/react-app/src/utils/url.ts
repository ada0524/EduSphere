export const backendUrl: string = "http://localhost:9000";

export const registerPath: string = "/auth-service/signup";
export const loginPath: string = "/auth-service/login";

export const getAllPostsPath: string = "/post-and-reply-service/posts/all";
export const getPostByIdPath = (postId: string): string =>
  `/post-and-reply-service/posts/${postId}`;

// export const createPostPath: string = '/post-service/posts/create';
export const createPostPath: string = '/post-and-reply-service/posts/create';
export const createPostPublishedPath: string = createPostPath + '?action=PUBLISHED';
export const createPostDraftPath: string = createPostPath + '?action=DRAFT';

export const fileServicePost: string = '/file-service/file/post';
export const uploadPostImages: string = fileServicePost + '/upload-images';
export const uploadPostAttachments: string = fileServicePost + '/upload-attachments';
export const getPostImages: string = fileServicePost + 'images';
export const getPostAttachments: string = fileServicePost + 'attachments';

export const fileServiceUser: string = '/file-service/file/user';
export const uploadProfileImage: string = fileServiceUser + '/upload-profile';
export const getProfileImage: string = fileServiceUser + '/profile';
export const getUserByIdPath = (userId: string): string =>
  `/user-service/getUserById?id=${userId}`;


export const getAllUsersPath: string = "/user-service/getAll";

export const updateUserStatusPath: string =
  "/user-service/updateStatus/{userId}";
