/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SboxTestModule } from '../../../test.module';
import { ApiComponent } from '../../../../../../main/webapp/app/entities/api/api.component';
import { ApiService } from '../../../../../../main/webapp/app/entities/api/api.service';
import { Api } from '../../../../../../main/webapp/app/entities/api/api.model';

describe('Component Tests', () => {

    describe('Api Management Component', () => {
        let comp: ApiComponent;
        let fixture: ComponentFixture<ApiComponent>;
        let service: ApiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [ApiComponent],
                providers: [
                    ApiService
                ]
            })
            .overrideTemplate(ApiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Api(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.apis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
