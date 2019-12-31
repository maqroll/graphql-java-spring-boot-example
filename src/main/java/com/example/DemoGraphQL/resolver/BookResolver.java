package com.example.DemoGraphQL.resolver;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.apache.commons.collections4.IterableUtils;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.model.Book;
import com.example.DemoGraphQL.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;
    private BatchLoader<Long,Author> authorBatchLoader;
    private DataLoader<Long,Author> authorLoader;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
      
        /*
        this.authorBatchLoader = new BatchLoader<Long, Author>() {
            @Override
            public CompletionStage<List<Author>> load(List<Long> authorIds) {
                return CompletableFuture.supplyAsync(() -> {
                    return IterableUtils.toList(authorRepository.findAll(authorIds));
                });
            }
        };
        
        this.authorLoader = DataLoader.newDataLoader(this.authorBatchLoader);*/
    }

    public Author getAuthor(Book book) {
    	//CompletableFuture<Author> load = this.authorLoader.load(book.getAuthor().getId());
    	return authorRepository.findById(book.getAuthor().getId()).orElse(null);
    }
}
