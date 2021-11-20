/*
 * Copyright 2021 creationreborn.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.creationreborn.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PostModel implements Comparable<PostModel> {
    
    @SerializedName("post_id")
    private int postId;
    
    @SerializedName("thread_id")
    private int threadId;
    
    @SerializedName("username")
    private String username;
    
    @SerializedName("post_date")
    private long postDate;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("message_state")
    private String messageState;
    
    @SerializedName("last_edit_date")
    private long lastEditDate;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("initial")
    private boolean initial;
    
    public int getPostId() {
        return postId;
    }
    
    public int getThreadId() {
        return threadId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public long getPostDate() {
        return postDate;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getMessageState() {
        return messageState;
    }
    
    public long getLastEditDate() {
        return lastEditDate;
    }
    
    public String getTitle() {
        return title;
    }
    
    public boolean isInitial() {
        return initial;
    }
    
    @Override
    public int compareTo(PostModel o) {
        return Objects.compare(getPostId(), o.getPostId(), Integer::compareTo);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        PostModel post = (PostModel) obj;
        return Objects.equals(getPostId(), post.getPostId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getPostId());
    }
}