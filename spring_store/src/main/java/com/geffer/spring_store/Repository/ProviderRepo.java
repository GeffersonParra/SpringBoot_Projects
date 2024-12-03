package com.geffer.spring_store.Repository;

import com.geffer.spring_store.Entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepo extends JpaRepository<Provider, Long> {
}
