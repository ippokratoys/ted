package application.services;

import application.database.*;
import application.database.repositories.ApartmentRepository;
import application.database.repositories.BookInfoRepository;
import application.database.repositories.BookReviewRepository;
import application.database.repositories.HostReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    BookInfoRepository bookInfoRepository;

    @Autowired
    ApartmentRepository apartmentRepository;

    @Autowired
    BookReviewRepository bookReviewRepository;

    @Autowired
    HostReviewRepository hostReviewRepository;


    public void createBookReview(int bookId,String comment,double rating,int apartmentId) throws Exception{
        BookInfo book=bookInfoRepository.findOne(bookId);
        if(book==null){
            throw new Exception("There is not a book");
        }
        Apartment apartment=apartmentRepository.findOne(apartmentId);
        if(apartment==null){
            throw new Exception("The apartment does not exist");
        }
        if(book.getBookReviews().size()>0){
            throw new Exception("There is a review you can not do more");
        }
        BookReview newBookReview=new BookReview();
        newBookReview.setApartment(apartment);
        newBookReview.setBookInfo(book);
        newBookReview.setComment(comment);
        newBookReview.setRating(rating);
        Date date=new Date();
        newBookReview.setTime(date);
        bookReviewRepository.save(newBookReview);
        book.setReviewDone(true);
        bookInfoRepository.save(book);
    }

    public void createHostReview(int bookId,String content,double rating,String username)throws Exception{
        HostReview hostReview=new HostReview();
        BookInfo book=bookInfoRepository.findOne(bookId);
        if(book==null){
            throw new Exception("The book does not exist");
        }
        if(book.getHostReviews().size()>0){
            throw new Exception("There is a review you can not do more");
        }
        Login login=book.getApartment().getLogin();
        hostReview.setBookInfo(book);
        hostReview.setContent(content);
        hostReview.setRating(rating);
        hostReview.setFrom_username(username);
        hostReview.setLogin(login);
        book.setHostReviewDone(true);
        bookInfoRepository.save(book);
        hostReviewRepository.save(hostReview);
    }

    public double getApartmentAvg(int  apartmentId){
        System.out.println("apartment = [" + apartmentId + "]");
        Apartment apartment=apartmentRepository.findOne(apartmentId);
        List<BookReview> bookReviews =apartment.getBookReviews();
        double sumOfRatings=0;
        if(bookReviews.size()==0)return 0;
        for(BookReview bookReview:bookReviews)
            sumOfRatings+=bookReview.getRating();
        double avrRating=sumOfRatings/bookReviews.size();
        return avrRating;
    }
}
