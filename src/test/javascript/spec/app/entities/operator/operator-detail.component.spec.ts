/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SboxTestModule } from '../../../test.module';
import { OperatorDetailComponent } from '../../../../../../main/webapp/app/entities/operator/operator-detail.component';
import { OperatorService } from '../../../../../../main/webapp/app/entities/operator/operator.service';
import { Operator } from '../../../../../../main/webapp/app/entities/operator/operator.model';

describe('Component Tests', () => {

    describe('Operator Management Detail Component', () => {
        let comp: OperatorDetailComponent;
        let fixture: ComponentFixture<OperatorDetailComponent>;
        let service: OperatorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [OperatorDetailComponent],
                providers: [
                    OperatorService
                ]
            })
            .overrideTemplate(OperatorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperatorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperatorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Operator(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.operator).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
