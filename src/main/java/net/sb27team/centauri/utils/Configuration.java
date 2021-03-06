/*
 * Copyright (c) 2018 SinC (superblaubeere27, Cubixy, Xc3pt1on, Cython)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWAR
 */

package net.sb27team.centauri.utils;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/*
 * Created by Cubxity on 18/05/2018
 */
public class Configuration {
    private JsonObject json;
    private File config = new File(System.getProperty("user.home"), "centauri.json");
    private JsonParser parser = new JsonParser();
    private Gson gson = new Gson();

    public Configuration() {
        if (config.exists())
            load();
        else json = new JsonObject();
    }

    private void load() {
        try {
            json = parser.parse(Files.toString(config, Charset.defaultCharset())).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.write(json.toString(), config, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T get(String key, T def) {
        if (json.has(key))
            return (T) gson.fromJson(json.get(key), def.getClass());
        json.add(key, gson.toJsonTree(def));
        return def;
    }

    public void set(String key, Object val) {
        json.add(key, gson.toJsonTree(val));
    }
}
