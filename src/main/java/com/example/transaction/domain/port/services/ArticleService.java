package com.example.transaction.domain.port.services;

import com.example.transaction.domain.model.entity.ArticleSaleCommand;
import com.example.transaction.domain.model.entity.Sale;

import java.util.List;

public interface ArticleService {
    List<Sale> getArticlesOnlyIds(List<ArticleSaleCommand> ids, Long id);
}
