package com.filmverleih.filmverleih;

import java.util.Optional;

public class NWayControllerConnector<Q,R,S,T,U,V,W,X,Y,Z> {

    private Q navbarController;
        private R libraryController;
        private S movieController;
        private T rentalController;
        private U settingsController;
        private V filterController;
        private Optional <W> controller7;
        private Optional <X> controller8;
        private Optional <Y> controller9;
        private Optional <Z> controller10;
        public NWayControllerConnector(Q controller1, R controller2, S controller3, T controller4, U controller5, V controller6) {
            this.navbarController = controller1;
            this.libraryController = controller2;
            this.movieController = controller3;
            this.rentalController = controller4;
            this.settingsController = controller5;
            this.filterController = controller6;
            this.controller7 = Optional.empty();
            this.controller8 = Optional.empty();
            this.controller9 = Optional.empty();
            this.controller10 = Optional.empty();
        }
        public NWayControllerConnector(Q controller1, R controller2, S controller3, T controller4, U controller5, V controller6, W controller7, X controller8, Y controller9, Z controller10) {
            this.navbarController = controller1;
            this.libraryController = controller2;
            this.movieController = controller3;
            this.rentalController = controller4;
            this.settingsController = controller5;
            this.filterController = controller6;
            this.controller7 = Optional.ofNullable(controller7);
            this.controller8 = Optional.ofNullable(controller8);
            this.controller9 = Optional.ofNullable(controller9);
            this.controller10 = Optional.ofNullable(controller10);
    }
    public Q getNavbarController() {
        return navbarController;
    }

    public R getLibraryController() {
        return libraryController;
    }

    public S getMovieController() {
        return movieController;
    }

    public T getRentalController() {
        return rentalController;
    }

    public U getSettingsController() {
        return settingsController;
    }

    public V getFilterController() {
        return filterController;
    }

    public Optional<W> getController7() {
        return controller7;
    }

    public Optional<X> getController8() {
        return controller8;
    }

    public Optional<Y> getController9() {
        return controller9;
    }

    public Optional<Z> getController10() {
        return controller10;
    }

}