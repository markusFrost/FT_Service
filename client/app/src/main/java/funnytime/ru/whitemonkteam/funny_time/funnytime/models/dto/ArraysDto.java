package funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;

/**
 * Created by Андрей on 24.05.2015.
 */
public class ArraysDto
{
    @SerializedName("films_add")
    public ArrayList<Film> FilmsAdd;

    @SerializedName("serials_add")
    public ArrayList<Serial> SerialsAdd;

    @SerializedName("books_add")
    public ArrayList<Book> BooksAdd;

    @SerializedName("films_update")
    public ArrayList<Film> FilmsUpdate;

    @SerializedName("serials_update")
    public ArrayList<Serial> SerialsUpdate;

    @SerializedName("books_update")
    public ArrayList<Book> BooksUpdate;

    @SerializedName("films_delete")
    public ArrayList<AnswerDto> FilmsDelete;

    @SerializedName("serials_delete")
    public ArrayList<AnswerDto> SerialsDelete;

    @SerializedName("books_delete")
    public ArrayList<AnswerDto> BooksDelete;

    public ArraysDto()
    {
        FilmsAdd = new ArrayList<>();
        SerialsAdd = new ArrayList<>();
        BooksAdd = new ArrayList<>();

        FilmsUpdate = new ArrayList<>();
        SerialsUpdate = new ArrayList<>();
        BooksUpdate = new ArrayList<>();

        FilmsDelete = new ArrayList<>();
        SerialsDelete = new ArrayList<>();
        BooksDelete = new ArrayList<>();
    }
}
