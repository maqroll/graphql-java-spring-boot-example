package com.example.DemoGraphQL.resolver;

import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.model.Book;

import graphql.kickstart.execution.context.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;

public class BookResolver implements GraphQLResolver<Book> {
	
    public BookResolver() {
    }
    
    public CompletableFuture<Author> getAuthor(Book book, DataFetchingEnvironment dfe) {
        final DataLoader<Long, Author> dataloader = ((GraphQLContext) dfe.getContext())
                .getDataLoaderRegistry().get()
                .getDataLoader("authorDataLoader");

        return dataloader.load(book.getAuthor().getId());
    }
}
