package com.sda.hibernate;

import com.sda.hibernate.entity.BooksEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    private static final org.hibernate.SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static org.hibernate.Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        Session session = ourSessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            List<BooksEntity> books = session.createQuery("FROM " + BooksEntity.class.getName()).list();
            for (BooksEntity book : books) {
                System.out.println(" ID: "        + book.getId());
                System.out.println(" Title: "     + book.getTitle());
                System.out.println(" Author: "    + book.getAuthor());
                System.out.println(" Category: "  + book.getCategory());
                System.out.println(" Pages: "     + book.getPageCount());
                System.out.println(" Price: "     + book.getPrice());
                System.out.println(" Publisher: " + book.getPublisher());
                System.out.println(" Published: " + book.getPublished());
                System.out.println(" ISBN: "      + book.getIsbn());
                System.out.println(" Availability: " + book.getOnStock());
                System.out.println(" ------------- ");
            }
            tx.commit();

            tx = session.beginTransaction();

            BooksEntity booksEntity = new BooksEntity();
            booksEntity.setAuthor("Marcin Kroszel");
            booksEntity.setOnStock(1);
            booksEntity.setTitle("Java Journey");
            session.save(booksEntity);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}





