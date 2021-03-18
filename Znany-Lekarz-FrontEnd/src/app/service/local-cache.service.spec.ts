import { TestBed } from '@angular/core/testing';

import { LocalCacheService } from './local-cache.service';

describe('LocalCacheService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LocalCacheService = TestBed.get(LocalCacheService);
    expect(service).toBeTruthy();
  });
});
