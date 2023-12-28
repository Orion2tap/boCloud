@PostMapping("/ajax/todo/all")
@ResponseBody
public String ajaxTodoAll(@RequestBody String jsonString) {
    Utility.log("[json]:%s", jsonString);
    JSONObject object = JSON.parseObject(jsonString);
    Utility.log("[object]:%s", object);

    ArrayList<TodoModel> todos = todoService.all();
    return JSON.toJSONString(todos);
}
