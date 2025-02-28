package VirtualAssistantSystem;
import java.sql.*;
import java.util.Scanner;
public class VirtualAssistantSystem {
	private static final String URL = "jdbc:mysql://localhost:3306/assistant_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static Connection connection;
    
    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Database!");
            runMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nHello, I am your personal Virtual Assistant Invoice System!");
            System.out.println("\n My name is Chris The Great! What can I do for you?");
            System.out.println("1. Client Management");
            System.out.println("2. Service Management");
            System.out.println("3. Invoice Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: manageClients(scanner); break;
                case 2: manageServices(scanner); break;
                case 3: manageInvoices(scanner); break;
                case 4: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void manageClients(Scanner scanner) {
        System.out.println("\n=== Client Management ===");
        System.out.println("1. Add Client");
        System.out.println("2. View Clients");
        System.out.println("3. Update Client");
        System.out.println("4. Delete Client");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: addClient(scanner); break;
            case 2: viewClients(); break;
            case 3: updateClient(scanner); break;
            case 4: deleteClient(scanner); break;
            default: System.out.println("Invalid choice.");
        }
    }

    private static void addClient(Scanner scanner) {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter client email: ");
        String email = scanner.nextLine();

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO clients (name, email) VALUES (?, ?)");) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Client added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewClients() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clients");) {
            System.out.println("\nClients:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("name") + " - " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateClient(Scanner scanner) {
        viewClients();
        System.out.print("Enter client ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        try (PreparedStatement stmt = connection.prepareStatement("UPDATE clients SET name=?, email=? WHERE id=?")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Client updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteClient(Scanner scanner) {
        viewClients();
        System.out.print("Enter client ID to delete: ");
        int id = scanner.nextInt();

        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM clients WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Client deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void manageServices(Scanner scanner) {
        System.out.println("\nService management coming soon!");
    }

    private static void manageInvoices(Scanner scanner) {
        System.out.println("\nInvoice management coming soon!");
    }
}
