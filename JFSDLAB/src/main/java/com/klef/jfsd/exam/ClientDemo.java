package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration con = new Configuration();
        con.configure("hibernate.cfg.xml");
        con.addAnnotatedClass(Department.class);

        SessionFactory sf = con.buildSessionFactory();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter Department ID to update: ");
            int deptId = sc.nextInt();
            sc.nextLine(); 

            System.out.println("Enter new Department Name: ");
            String newName = sc.nextLine();

            System.out.println("Enter new Location: ");
            String newLocation = sc.nextLine();

            String hqlUpdate = "UPDATE Department SET name = ?1, location = ?2 WHERE deptId = ?3";
            Query q = s.createQuery(hqlUpdate);
            q.setParameter(1, newName);
            q.setParameter(2, newLocation);
            q.setParameter(3, deptId);

            int result = q.executeUpdate();
            System.out.println(result + " record(s) updated.");

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            s.close();
            sf.close();
        }
    }
}
