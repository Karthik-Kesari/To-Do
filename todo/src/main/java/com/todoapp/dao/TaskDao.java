package com.todoapp.dao;

import java.text.AttributedCharacterIterator.Attribute;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import com.todoapp.config.DynamoDbConfig;
import com.todoapp.model.Task; 

@Repository
public class TaskDao {
    private DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
    public TaskDao(DynamoDbConfig dynamoDbConfig) {
        if(dynamoDbConfig == null) {
        System.out.println("DynamoDbConfig is null");
    }else
        this.dynamoDbConfig = dynamoDbConfig;
        
    }
    
    private final DynamoDbClient dynamoDbClient = dynamoDbConfig.dynamoDbClient();
    private final String tableName = "Tasks";


    public Task save(Task task) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(task.getId()).build());
        item.put("title",AttributeValue.builder().s(task.getTitle()).build());
        item.put("description",AttributeValue.builder().s(task.getDescription()).build());
        item.put("completed", AttributeValue.builder().bool(task.isCompleted()).build());
        String currentTime;
        if(task.getCreatedAt()==null)
        {
            currentTime = Instant.now().toString();
            task.setCreatedAt(currentTime);
        }
        else
        {
            currentTime = task.getCreatedAt();
        }
        item.put("createdAt", AttributeValue.builder().s(task.getCreatedAt()).build());
        item.put("updatedAt", AttributeValue.builder().s("-").build());
        PutItemRequest request = PutItemRequest.builder().tableName("Tasks").item(item).build();
        dynamoDbClient.putItem(request);
        System.out.println("place task "+task.getId());
        return task; // Placeholder return statement
    }
    public Task getTaskById(String id) {
        Map<String, AttributeValue> key = Map.of(
            "id", AttributeValue.builder().s(id).build()
        );
        GetItemRequest getItemRequest = GetItemRequest.builder()
            .tableName("Tasks")
            .key(key)
            .build();
        Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();
        if (item == null || item.isEmpty()) {
            return null; // Task not found
        }
        Task task = new Task();
        task.setId(item.get("id").s());
        task.setTitle(item.get("title").s());   
        task.setDescription(item.get("description").s());
        task.setCompleted((item.get("completed").bool()));
        task.setCreatedAt((item.get("createdAt").s()));
        task.setUpdatedAt((item.get("updatedAt").s()));    


        return task; // Placeholder return statement
    }
    public List<Task> getTasks(){
        ScanRequest scanrequest = ScanRequest.builder().tableName("Tasks").build();
        ScanResponse response = dynamoDbClient.scan(scanrequest);

        List<Task> tasks = new ArrayList<>();
        for(Map<String, AttributeValue> item : response.items())
        {
            Task task = new Task();
            task.setId(item.get("id").s());
            task.setTitle(item.get("title").s());
            task.setDescription(item.get("description").s());
            task.setCompleted(item.get("completed").bool());
            task.setCreatedAt((item.get("createdAt").s()));
            task.setUpdatedAt((item.get("updatedAt").s())); 
            tasks.add(task);
            System.out.println("VS code to fargate ");
        }
        return tasks;
    }
    public Task updateTask(String id, Task task) {

        Map<String, AttributeValue> key = Map.of("id",AttributeValue.builder().s(id).build());

        Map<String, AttributeValueUpdate> updatedTask = new HashMap<>();

        if(task.getTitle() != null){
                 updatedTask.put("title",AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(task.getTitle()).build())
                    .action(AttributeAction.PUT)
                    .build());
            }
        if(task.getDescription() != null){
              updatedTask.put("description", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(task.getDescription()).build())
                    .action(AttributeAction.PUT)
                    .build());
            }
        if(task.isCompleted() || (task.isCompleted())){
                updatedTask.put("completed", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().bool(task.isCompleted()).build())
                    .action(AttributeAction.PUT)
                    .build());
            }
        String currentTime = Instant.now().toString();
        updatedTask.put("updatedAt", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(currentTime).build())
                    .action(AttributeAction.PUT)
                    .build());

        if (!updatedTask.isEmpty()) {
            UpdateItemRequest request = UpdateItemRequest.builder()
                                .tableName("Tasks")
                                .key(key)
                                .attributeUpdates(updatedTask)
                                .build();   
            dynamoDbClient.updateItem(request);
        }
        return task;
        
    }
}
