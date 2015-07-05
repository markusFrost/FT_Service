package funnytime.ru.whitemonkteam.funny_time.funnytime.utils;

import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;

/**
 * Created by Андрей on 19.05.2015.
 */
public class ConvertUtils
{
    public static UserDto decodeUserDto(UserDto userDto)
    {
        if ( userDto.first_name == null)
        {
            userDto.first_name = "";
        }
        if ( userDto.last_name == null)
        {
            userDto.last_name = "";
        }
        if ( userDto.photo_200 == null)
        {
            userDto.photo_200 = "";
        }

        StringCrypter crypter = new StringCrypter();
        userDto.first_name = crypter.decrypt(userDto.first_name);
        userDto.last_name = crypter.decrypt(userDto.last_name);
        userDto.photo_200 = crypter.decrypt(userDto.photo_200);

        return userDto;
    }

    public static Film encyptFilm(Film item)
    {

        if (item.Name == null) {
            item.Name = "";
        }
        if (item.ImageURL == null) {
            item.ImageURL = "";
        }
        if (item.Comment == null) {
            item.Comment = "";
        }
        if (item.ImagePath == null) {
            item.ImagePath = "";
        }

        StringCrypter crypter = new StringCrypter();
        item.Name = crypter.encrypt(item.Name);
        item.ImageURL = crypter.encrypt(item.ImageURL);
        item.Comment = crypter.encrypt(item.Comment);
        item.ImagePath = crypter.encrypt(item.ImagePath);


        return item;
    }

    public static Film decodeFilm(Film item)
    {
        StringCrypter crypter = new StringCrypter();
        item.Name = crypter.decrypt(item.Name);
        item.ImageURL = crypter.decrypt(item.ImageURL);
        item.Comment = crypter.decrypt(item.Comment);
        item.ImagePath = crypter.decrypt(item.ImagePath);
        item.NoteId = item.Id;

        return item;
    }


    public static Serial encyptSerial(Serial item)
    {

        if (item.Name == null) {
            item.Name = "";
        }
        if (item.ImageURL == null) {
            item.ImageURL = "";
        }
        if (item.Comment == null) {
            item.Comment = "";
        }
        if (item.ImagePath == null) {
            item.ImagePath = "";
        }

        StringCrypter crypter = new StringCrypter();
        item.Name = crypter.encrypt(item.Name);
        item.ImageURL = crypter.encrypt(item.ImageURL);
        item.Comment = crypter.encrypt(item.Comment);
        item.ImagePath = crypter.encrypt(item.ImagePath);


        return item;
    }

    public static Serial decodeSerial(Serial item)
    {
        StringCrypter crypter = new StringCrypter();
        item.Name = crypter.decrypt(item.Name);
        item.ImageURL = crypter.decrypt(item.ImageURL);
        item.Comment = crypter.decrypt(item.Comment);
        item.ImagePath = crypter.decrypt(item.ImagePath);
        item.NoteId = item.Id;

        return item;
    }

    public static Book encyptBook(Book item)
    {

        if (item.Name == null) {
            item.Name = "";
        }
        if (item.ImageURL == null) {
            item.ImageURL = "";
        }
        if (item.Comment == null) {
            item.Comment = "";
        }
        if (item.ImagePath == null) {
            item.ImagePath = "";
        }
        if (item.Author == null) {
            item.Author = "";
        }

        StringCrypter crypter = new StringCrypter();
        item.Name = crypter.encrypt(item.Name);
        item.ImageURL = crypter.encrypt(item.ImageURL);
        item.Comment = crypter.encrypt(item.Comment);
        item.ImagePath = crypter.encrypt(item.ImagePath);
        item.Author = crypter.encrypt(item.Author);


        return item;
    }

    public static Book decodeBook(Book item)
    {
        StringCrypter crypter = new StringCrypter();
        item.Name = crypter.decrypt(item.Name);
        item.ImageURL = crypter.decrypt(item.ImageURL);
        item.Comment = crypter.decrypt(item.Comment);
        item.ImagePath = crypter.decrypt(item.ImagePath);
        item.Author = crypter.decrypt(item.Author);
        item.NoteId = item.Id;

        return item;
    }


}
