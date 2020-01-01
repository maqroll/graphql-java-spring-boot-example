package com.example.DemoGraphQL;

import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.apache.commons.collections4.IterableUtils;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.repository.AuthorRepository;

import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.servlet.context.DefaultGraphQLServletContext;
import graphql.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.servlet.context.GraphQLServletContextBuilder;

@Component
public class CustomGraphQLCustomBuilder implements GraphQLServletContextBuilder {

	private final AuthorRepository authorRepository;

	public CustomGraphQLCustomBuilder(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public GraphQLContext build() {
		return new DefaultGraphQLContext(buildDataLoaderRegistry(), null);	
	}

	@Override
	public GraphQLContext build(HttpServletRequest req, HttpServletResponse res) {
	    return DefaultGraphQLServletContext.createServletContext(buildDataLoaderRegistry(), null)
	    		.with(req)
	    		.with(res)
	            .build();	
	}

	@Override
	public GraphQLContext build(Session sess, HandshakeRequest req) {
	    return DefaultGraphQLWebSocketContext.createWebSocketContext(buildDataLoaderRegistry(), null)
	    		.with(sess)
	            .with(req)
	            .build();	
	}

	private DataLoaderRegistry buildDataLoaderRegistry() {
		DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
		dataLoaderRegistry.register("authorDataLoader",
				new DataLoader<Long, Author>(authorIds ->
				CompletableFuture.supplyAsync(() ->
				IterableUtils.toList(authorRepository.findAllById(authorIds)))));
		return dataLoaderRegistry;
	}	
}
