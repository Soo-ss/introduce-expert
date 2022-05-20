package com.backend.account;

import com.backend.account.form.TestForm;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/test")
    public String test(){
        JsonObject obj = new JsonObject();

        obj.addProperty("sample1", "sample1-data");
        obj.addProperty("sample2", "sample2-data");

        JsonObject data = new JsonObject();
        data.addProperty("data1", "data1-data");
        data.addProperty("data2", 12345);

        obj.add("bigData", data);

        return obj.toString();
    }

    @PostMapping("/get-data")
    public String getTest(@RequestBody TestForm testForm){
        JsonObject obj = new JsonObject();

        obj.addProperty("sample1", "sample1-data");
        obj.addProperty("sample2", "sample2-data");

        JsonObject data = new JsonObject();
        data.addProperty("data1", "data1-data");
        data.addProperty("data2", 12345);

        obj.add("bigData", data);

        return obj.toString();
    }
}
