import React from 'react';
import { Message } from './Message';

interface MessageComponentProps {
    message: Message;
    updateMessageInState: (id: number, newStatus: string) => void;
}

const MessageComponent: React.FC<MessageComponentProps> = ({ message, updateMessageInState }) => {



    const updateStatus = () => {

        const token: string | null = localStorage.getItem("token");

        fetch(`http://localhost:9000/message-service/messages/${message.messageId}?status=${message.status == "Open" ? "Close" : "Open"}`, {
            method: 'PATCH',
            headers: {
                /* 'Authorization': "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoUmVnQEF1dGhSZWcuY29tIiwidXNlcklkIjo2LCJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiVXNlciJ9LHsiYXV0aG9yaXR5IjoiYWN0aXZlIn1dfQ.TWTDB-veNbx4xpxmJgA1eekfUhUOuzL44-P3FWFz1OY" */
                'Authorization': "Bearer " + token
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Http error! status: ${response.status}`)
                }
                return response.json();
            })
            .then(updatedMessage => {
                updateMessageInState(message.messageId, message.status == "Open" ? "Close" : "Open");
            })
            .catch(error => console.error('Error updating message:', error));
    };



    return (
        <tr key={message.messageId}>
            <td>{message.messageId}</td>
            <td>{message.userId}</td>
            <td>{message.email}</td>
            <td>{message.subject}</td>
            <td>{message.message_text}</td>
            <td>{message.status}</td>
            <td>
                <button onClick={() => updateStatus()}>{message.status == "Open" ? "Close" : "Open"}</button>
            </td>
        </tr>
    );
};

export default MessageComponent;