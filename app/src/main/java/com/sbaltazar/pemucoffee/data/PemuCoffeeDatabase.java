package com.sbaltazar.pemucoffee.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sbaltazar.pemucoffee.data.dao.BrewMethodDao;
import com.sbaltazar.pemucoffee.data.dao.RecipeDao;
import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.data.entities.Recipe;

import java.lang.reflect.Type;
import java.util.List;

@Database(entities = {
        Recipe.class,
        BrewMethod.class
}, version = 2, exportSchema = false)
@TypeConverters({PemuCoffeeDatabase.Converters.class})
public abstract class PemuCoffeeDatabase extends RoomDatabase {

    //DAOs
    public abstract RecipeDao recipeDao();
    public abstract BrewMethodDao brewMethodDao();

    // Singleton Database
    private static volatile PemuCoffeeDatabase INSTANCE;

    public static PemuCoffeeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PemuCoffeeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PemuCoffeeDatabase.class, "pemucoffee_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static class Converters {
        @TypeConverter
        public static List<String> fromString(String value) {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromList(List<String> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }
    }

}
