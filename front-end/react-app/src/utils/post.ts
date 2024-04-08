

// TODO: The backend is returning the whole post class, which is not necessary for the front-end.
export interface Post {
    postId: string;
    userId: number;
    title: string;
    content: string;
    isArchived: boolean;
    status: 'PUBLISHED' | 'UNPUBLISHED';
    dateCreated: string;
    dateModified: string;
    images: string[]; 
    attachments: string[];
    postReplies: null | PostReply[]; // Assuming it could be an array of replies or null, specify further if possible
  }

export interface PostReply {
    replyId: string | null; // Assuming replyId can be null based on the given structure
    userId: number | null; // Assuming userId can be null
    comment: string;
    isActive: boolean;
    dateCreated: string;
    subReplies: SubReply[] | null; // Array of SubReply objects or null
}
  
export interface SubReply {
    subReplyId: string | null; // Assuming subReplyId can be null
    userId: number | null; // Assuming userId can be null
    comment: string;
    isActive: boolean;
    dateCreated: string;
}
