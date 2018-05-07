/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SboxTestModule } from '../../../test.module';
import { UserAddonDetailComponent } from '../../../../../../main/webapp/app/entities/user-addon/user-addon-detail.component';
import { UserAddonService } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.service';
import { UserAddon } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.model';

describe('Component Tests', () => {

    describe('UserAddon Management Detail Component', () => {
        let comp: UserAddonDetailComponent;
        let fixture: ComponentFixture<UserAddonDetailComponent>;
        let service: UserAddonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [UserAddonDetailComponent],
                providers: [
                    UserAddonService
                ]
            })
            .overrideTemplate(UserAddonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAddonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserAddon(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userAddon).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
