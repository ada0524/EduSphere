import React, { useEffect, useState } from 'react';

interface History {
    postId: string;
    content: string;
    viewTime: string;
    title: string;
}

interface HistoryProps {
    userId: number;
}


const HistoryList: React.FC<HistoryProps> = ({userId}) => {
    const [histories, sethistories] = useState<History[]>([]);
    const [filter, setFilter] = useState<string>(''); // For user to type in a filter

    useEffect(() => {
        fetchMessages();
    }, []);

    const fetchMessages = async () => {
        const token: string | null = localStorage.getItem("token");

        const baseUrl = `http://localhost:9000/history-service/users/${userId}/view-history`;

        const url = filter ? `${baseUrl}?keyWord=${filter}` : baseUrl;
        try {
            const response = await fetch(url, {
                headers: {
                    /* 'Authorization': "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoUmVnQEF1dGhSZWcuY29tIiwidXNlcklkIjo2LCJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiVXNlciJ9LHsiYXV0aG9yaXR5IjoiYWN0aXZlIn1dfQ.TWTDB-veNbx4xpxmJgA1eekfUhUOuzL44-P3FWFz1OY" */
                    'Authorization': "Bearer " + token
                }
            });
            if (!response.ok) {
                throw new Error('Failed to fetch messages');
            }
            const {data}: {data: History[]} = await response.json();
            sethistories(data);
        } catch (error) {
            console.error('Error fetching messages:', error);
        }
    };

    // Call fetchMessages again with key-word filter
    const handleRefetch = () => {
        fetchMessages();
    };

    return (
        <div>
            <div>
                <input
                    type="text"
                    placeholder="Enter Keyword for filtering histories"
                    value={filter}
                    onChange={(e) => setFilter(e.target.value)}
                />
                <button onClick={handleRefetch}>Refetch History</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Content</th>
                        <th>View Date</th>
                    </tr>
                </thead>
                <tbody>
                    {histories.map((history) => (
                        <tr key={history.postId}>
                            <td>{history.title}</td>
                            <td>{history.content}</td>
                            <td>{history.viewTime}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default HistoryList;