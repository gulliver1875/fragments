package com.gravity.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WowCubeDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wowcube.db";
    private static final String TABLE_NAME = "cards";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ALLY_CLASS = "ally_class";
    private static final String COLUMN_ATTACK_VALUE = "attack_value";
    private static final String COLUMN_ATTACK_TYPE = "attack_type";
    private static final String COLUMN_BLOCK_VALUE = "block";
    private static final String COLUMN_DEFENSE_VALUE = "defense_value";
    private static final String COLUMN_FACTION = "faction";
    private static final String COLUMN_HEALTH_VALUE = "health_value";
    private static final String COLUMN_HERO_TYPE = "hero_type";
    private static final String COLUMN_IMAGE_LARGE = "image_large";
    private static final String COLUMN_IMAGE_SMALL = "image_small";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_PLAY_COST = "play_cost";
    private static final String COLUMN_RACE = "race";
    private static final String COLUMN_RARITY = "rarity";
    private static final String COLUMN_SET = "set";
    private static final String COLUMN_STRIKE_COST = "strike_cost";
    private static final String COLUMN_TEXT_BACK_EN = "text_back_en";
    private static final String COLUMN_TEXT_BACK_JA = "text_back_en";
    private static final String COLUMN_TEXT_FRONT_EN = "text_front_en";
    private static final String COLUMN_TEXT_FRONT_JA = "text_front_ja";
    private static final String COLUMN_TYPE = "type";

    private static final String DATABASE_CREATE =
        "CREATE TABLE " + TABLE_NAME
        + " ("
            + COLUMN_ID + INT_TYPE + COMMA_SEP
            + COLUMN_ALLY_CLASS + TEXT_TYPE + COMMA_SEP
	        + COLUMN_ATTACK_VALUE + INT_TYPE + COMMA_SEP
	        + COLUMN_ATTACK_TYPE + TEXT_TYPE + COMMA_SEP
	        + COLUMN_BLOCK_VALUE + TEXT_TYPE + COMMA_SEP
	        + COLUMN_DEFENSE_VALUE + INT_TYPE + COMMA_SEP
	        + COLUMN_FACTION + TEXT_TYPE + COMMA_SEP
            + COLUMN_HEALTH_VALUE + INT_TYPE + COMMA_SEP
            + COLUMN_HERO_TYPE + TEXT_TYPE + COMMA_SEP
            + COLUMN_IMAGE_LARGE + TEXT_TYPE + COMMA_SEP
            + COLUMN_IMAGE_SMALL + TEXT_TYPE + COMMA_SEP
            + COLUMN_NAME + TEXT_TYPE + COMMA_SEP
            + COLUMN_PLAY_COST + INT_TYPE + COMMA_SEP
            + COLUMN_RACE + TEXT_TYPE + COMMA_SEP
            + COLUMN_RARITY + TEXT_TYPE + COMMA_SEP
            + COLUMN_SET + TEXT_TYPE + COMMA_SEP
            + COLUMN_STRIKE_COST + INT_TYPE + COMMA_SEP
            + COLUMN_TEXT_BACK_EN + INT_TYPE + COMMA_SEP
            + COLUMN_TEXT_BACK_JA + INT_TYPE + COMMA_SEP
            + COLUMN_TEXT_FRONT_EN + INT_TYPE + COMMA_SEP
            + COLUMN_TEXT_FRONT_JA + INT_TYPE + COMMA_SEP
            + COLUMN_TYPE + TEXT_TYPE
        + ");"
    ;

    public static final String DATABASE_DELETE =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    public WowCubeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.w(WowCubeDbHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
        db.execSQL(DATABASE_DELETE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addCard(Card card)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, card.getId());
        values.put(COLUMN_ALLY_CLASS, card.getAllyClass());
        values.put(COLUMN_ATTACK_VALUE, card.getAttackValue());
        values.put(COLUMN_ATTACK_TYPE, card.getAttackType());
        values.put(COLUMN_BLOCK_VALUE, card.getBlockValue());
        values.put(COLUMN_DEFENSE_VALUE, card.getDefenseValue());
        values.put(COLUMN_FACTION, card.getFaction());
        values.put(COLUMN_HEALTH_VALUE, card.getHealthValue());
        values.put(COLUMN_HERO_TYPE, card.getHeroType());
        values.put(COLUMN_IMAGE_LARGE, card.getImageLarge());
        values.put(COLUMN_IMAGE_SMALL, card.getImageSmall());
        values.put(COLUMN_NAME, card.getName());
        values.put(COLUMN_PLAY_COST, card.getPlayCost());
        values.put(COLUMN_RACE, card.getRace());
        values.put(COLUMN_RARITY, card.getRarity());
        values.put(COLUMN_SET, card.getSet());
        values.put(COLUMN_STRIKE_COST, card.getStrikeCost());
        values.put(COLUMN_TEXT_BACK_EN, card.getTextBackEn());
        values.put(COLUMN_TEXT_BACK_JA, card.getTextBackJa());
        values.put(COLUMN_TEXT_FRONT_EN, card.getTextFrontEn());
        values.put(COLUMN_TEXT_FRONT_JA, card.getTextFrontJa());
        values.put(COLUMN_TYPE, card.getType());

        SQLiteDatabase db = this.getWritableDatabase();
        
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Card findCard(String cardName)
    {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " =  \"" + cardName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Card card = new Card();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            card.setId(Integer.parseInt(cursor.getString(0)));
            card.setAllyClass(cursor.getString(1));
            card.setAttackValue(Integer.parseInt(cursor.getString(2)));
            card.setAttackType(cursor.getString(3));
            card.setBlockValue(Integer.parseInt(cursor.getString(4)));
            card.setDefenseValue(Integer.parseInt(cursor.getString(5)));
            card.setFaction(cursor.getString(6));
            card.setHealthValue(Integer.parseInt(cursor.getString(7)));
            card.setHeroType(cursor.getString(8));
            card.setImageLarge(cursor.getString(9));
            card.setImageSmall(cursor.getString(10));
            card.setName(cursor.getString(11));
            card.setPlayCost(Integer.parseInt(cursor.getString(12)));
            card.setRace(cursor.getString(13));
            card.setRarity(cursor.getString(14));
            card.setSet(cursor.getString(15));
            card.setStrikeCost(Integer.parseInt(cursor.getString(16)));
            card.setTextBackEn(cursor.getString(17));
            card.setTextBackJa(cursor.getString(18));
            card.setTextFrontEn(cursor.getString(19));
            card.setTextFrontJa(cursor.getString(20));
            card.setType(cursor.getString(21));

            card.setName(cursor.getString(1));
            cursor.close();
        }
        else {
            card = null;
        }
        db.close();
        return card;
    }

    public boolean deleteCard(String cardName) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " =  \"" + cardName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Card card = new Card();

        if (cursor.moveToFirst()) {
            card.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(card.getId()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
