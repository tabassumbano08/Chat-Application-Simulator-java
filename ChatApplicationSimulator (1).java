import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ---------- User Class ----------
class User {
    private String username;
    private String status;

    public User(String username) {
        this.username = username;
        this.status = "Online";
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

// ---------- Message Class ----------
class Message {
    private String sender;
    private String content;
    private String timestamp;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender + ": " + content;
    }
}

// ---------- ChatRoom Class ----------
class ChatRoom {
    private User[] users;
    private int userCount;

    private Message[] messages;
    private int messageCount;

    public ChatRoom(int maxUsers, int maxMessages) {
        users = new User[maxUsers];
        userCount = 0;

        messages = new Message[maxMessages];
        messageCount = 0;
    }

    // Add a new user to the chat room
    public boolean addUser(User user) {
        if (userCount >= users.length) {
            System.out.println("Chat room is full! Cannot add more users.");
            return false;
        }
        users[userCount++] = user;
        return true;
    }

    // Send a message (store it in array)
    public boolean sendMessage(String sender, String content) {
        if (messageCount >= messages.length) {
            System.out.println("Chat history is full! Cannot store more messages.");
            return false;
        }
        Message msg = new Message(sender, content);
        messages[messageCount++] = msg;
        return true;
    }

    // Display chat history in order
    public void displayChatHistory() {
        System.out.println("\n----- Chat History -----");
        if (messageCount == 0) {
            System.out.println("No messages yet.");
            return;
        }
        for (int i = 0; i < messageCount; i++) {
            System.out.println(messages[i]);
        }
        System.out.println("-------------------------\n");
    }

    // Display all users in the chat room
    public void displayUsers() {
        System.out.println("\n----- Users in Chat Room -----");
        if (userCount == 0) {
            System.out.println("No users yet.");
            return;
        }
        for (int i = 0; i < userCount; i++) {
            System.out.println((i + 1) + ". " + users[i].getUsername()
                    + " (" + users[i].getStatus() + ")");
        }
        System.out.println("-------------------------------\n");
    }

    // Check if a username exists in the chat room
    public boolean userExists(String username) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public int getUserCount() {
        return userCount;
    }
}

// ---------- Main Class ----------
public class ChatApplicationSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create chat room with capacity for 10 users and 100 messages
        ChatRoom chatRoom = new ChatRoom(10, 100);

        System.out.println("=================================================");
        System.out.println("       WELCOME TO CHAT APPLICATION SIMULATOR");
        System.out.println("=================================================");

        // --------- Create Users ---------
        System.out.print("\nEnter number of users to create: ");
        int numUsers = readInt(sc);

        for (int i = 0; i < numUsers; i++) {
            System.out.print("Enter name for User " + (i + 1) + ": ");
            String name = sc.next();
            User user = new User(name);
            chatRoom.addUser(user);
        }

        chatRoom.displayUsers();

        // --------- Main Menu Loop ---------
        int choice;
        do {
            System.out.println("\n============ MAIN MENU ============");
            System.out.println("1. Send Message");
            System.out.println("2. View Chat History");
            System.out.println("3. View Users");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = readInt(sc);

            switch (choice) {
                case 1:
                    sendMessageFlow(sc, chatRoom);
                    break;

                case 2:
                    chatRoom.displayChatHistory();
                    break;

                case 3:
                    chatRoom.displayUsers();
                    break;

                case 4:
                    System.out.println("\nExiting Chat Application Simulator. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter 1-4.");
            }

        } while (choice != 4);

        sc.close();
    }

    // Handles sending a message: enter sender, validate, enter message, store it
    private static void sendMessageFlow(Scanner sc, ChatRoom chatRoom) {
        if (chatRoom.getUserCount() == 0) {
            System.out.println("No users available to send messages.");
            return;
        }

        System.out.print("Enter your username: ");
        String sender = sc.next();

        if (!chatRoom.userExists(sender)) {
            System.out.println("User not found in chat room!");
            return;
        }

        System.out.print("Enter your message: ");
        sc.nextLine(); // consume leftover newline
        String content = sc.nextLine();

        boolean success = chatRoom.sendMessage(sender, content);
        if (success) {
            System.out.println("Message sent successfully!");
        }
    }

    // Helper method to safely read integer input
    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
