import React, { useEffect, useState } from 'react';
import MessageComponent from './MessageComponent';
import { Message } from './Message';

const MessagesList: React.FC = () => {
  const [messages, setMessages] = useState<Message[]>([]);

  useEffect(() => {
    const fetchMessages = async () => {

        const token: string | null = localStorage.getItem("token");

      try {
        const response = await fetch('http://localhost:9000/message-service/messages', {
            headers: {
                /* 'Authorization': "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoUmVnQEF1dGhSZWcuY29tIiwidXNlcklkIjo2LCJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiQWRtaW4ifSx7ImF1dGhvcml0eSI6ImFjdGl2ZSJ9XX0.gUmT9_ciLwgBQVD7tG1BXsDuOEjK98LyWUPaNBnE16c" */
                'Authorization': "Bearer " + token
            }
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const {data}: {data: Message[]} = await response.json();
        setMessages(data);
      } catch (error) {
        console.error('Error fetching messages:', error);
      }
    };

    fetchMessages();
  }, []);

  const handleUpdate = (id: number, newStatus: string) => {
    setMessages(messages.map(message => message.messageId === id ? { ...message, status: newStatus } : message));
  };

  return (
    <table>
      <thead>
        <tr>
          <th>Message ID</th>
          <th>User ID</th>
          <th>Email</th>
          <th>Subject</th>
          <th>Text</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {messages.map(message => (
          <MessageComponent key={message.messageId} message={message} updateMessageInState={handleUpdate} />
        ))}
      </tbody>
    </table>
  );
};

export default MessagesList;