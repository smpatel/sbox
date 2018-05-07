/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SboxTestModule } from '../../../test.module';
import { CommissionGroupDetailComponent } from '../../../../../../main/webapp/app/entities/commission-group/commission-group-detail.component';
import { CommissionGroupService } from '../../../../../../main/webapp/app/entities/commission-group/commission-group.service';
import { CommissionGroup } from '../../../../../../main/webapp/app/entities/commission-group/commission-group.model';

describe('Component Tests', () => {

    describe('CommissionGroup Management Detail Component', () => {
        let comp: CommissionGroupDetailComponent;
        let fixture: ComponentFixture<CommissionGroupDetailComponent>;
        let service: CommissionGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [CommissionGroupDetailComponent],
                providers: [
                    CommissionGroupService
                ]
            })
            .overrideTemplate(CommissionGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommissionGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommissionGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CommissionGroup(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.commissionGroup).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
